package org.openhds.task.service.impl;

import java.util.Calendar;
import java.util.List;

import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.openhds.dao.service.Dao;
import org.openhds.domain.model.AsyncTask;
import org.openhds.task.service.AsyncTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.hibernate3.SessionHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Component
public class AsyncTaskServiceImpl implements AsyncTaskService {

    private SessionFactory sessionFactory;
    private Dao<AsyncTask, String> dao;

    @Autowired
    public AsyncTaskServiceImpl(SessionFactory sessionFactory, @Qualifier("taskDao") Dao<AsyncTask, String> dao) {
        this.sessionFactory = sessionFactory;
        this.dao = dao;
    }

    @Override
    public boolean taskShouldRun(String taskName) {
        return taskShouldRun(dao.findByProperty("taskName", taskName));
    }

    private boolean taskShouldRun(AsyncTask task) {
        return task == null || task.getTaskEndDate() != null;
    }

    /**
     * This code was lifted from the OpenSessionInViewFilter to properly open a session through Spring
     */
    @Override
    public void beginNewTaskSession() {
        Session session = SessionFactoryUtils.getSession(sessionFactory, true);
        session.setFlushMode(FlushMode.MANUAL);
        TransactionSynchronizationManager.bindResource(sessionFactory, new SessionHolder(session));
    }

    @Override
    @Transactional
    public void startTask(String taskName) {
        AsyncTask task = dao.findByProperty("taskName", taskName);
        if (task == null) {
            task = new AsyncTask();
            task.setTaskName(taskName);
        }
        task.setTaskEndDate(null);
        task.setTotalItems(0);
        task.setTaskStartDate(Calendar.getInstance());
        dao.saveOrUpdate(task);
    }

    @Override
    @Transactional
    public void updateTaskProgress(String taskName, long itemsWritten) {
        AsyncTask task = dao.findByProperty("taskName", taskName);
        task.setTotalItems(itemsWritten);
    }

    @Override
    @Transactional
    public void finishTask(String taskName, long itemsWritten, String md5) {
        AsyncTask task = dao.findByProperty("taskName", taskName);
        task.setTotalItems(itemsWritten);
        task.setTaskEndDate(Calendar.getInstance());
        task.setMd5Hash(md5);
    }

    /**
     * This code was lifted from the OpenSessionInViewFilter to properly close a session through Spring
     */
    @Override
    public void closeTaskSession() {
        SessionHolder sessionHolder = (SessionHolder) TransactionSynchronizationManager.unbindResource(sessionFactory);
        SessionFactoryUtils.closeSession(sessionHolder.getSession());
    }

    @Override
    public void clearSession() {
        sessionFactory.getCurrentSession().clear();
    }

    @Override
    public List<AsyncTask> findAllAsyncTask() {
        return dao.findAll(false);
    }

}
