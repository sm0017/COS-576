package org.openhds.task.service.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.openhds.dao.service.Dao;
import org.openhds.domain.model.AsyncTask;
import org.openhds.task.service.AsyncTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.List;

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

    // REQUIRES_NEW allows status updates to be written during long tasks
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
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

    // REQUIRES_NEW allows status updates to be written during long tasks
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateTaskProgress(String taskName, long itemsWritten) {
        AsyncTask task = dao.findByProperty("taskName", taskName);
        task.setTotalItems(itemsWritten);
    }

    // REQUIRES_NEW allows status updates to be written during long tasks
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void finishTask(String taskName, long itemsWritten, String md5) {
        AsyncTask task = dao.findByProperty("taskName", taskName);
        task.setTotalItems(itemsWritten);
        task.setTaskEndDate(Calendar.getInstance());
        task.setMd5Hash(md5);
    }

    @Override
    public void clearSession() {
        Session session = sessionFactory.getCurrentSession();
        session.flush();
        session.clear();
    }

    @Override
    public List<AsyncTask> findAllAsyncTask() {
        return dao.findAll(false);
    }

}
