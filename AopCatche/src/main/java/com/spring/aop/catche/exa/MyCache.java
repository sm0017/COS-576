package com.spring.aop.catche.exa;


import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

public class MyCache {

    private LinkedHashMap<String, Object> cacheMap = new  LinkedHashMap<String, Object>();
    private LinkedHashMap<String, Date> timeStampMap = new  LinkedHashMap<String, Date>();
    /**
     * defines the max size of hashmap
     */
    private long maxsize = 10;  //should come from properties file or some configuration
    /**
     * how long the object should be stored before it is evicted from cache
     */
    private long objectLifeTime = 10000;

    private boolean lock = false;

    public LinkedHashMap<String, Object> getCacheMap() {
        return cacheMap;
    }

    public void setCacheMap(LinkedHashMap<String, Object> cacheMap) {
        this.cacheMap = cacheMap;
    }

    public LinkedHashMap<String, Date> getTimeStampMap() {
        return timeStampMap;
    }

    public void setTimeStampMap(LinkedHashMap<String, Date> timeStampMap) {
        this.timeStampMap = timeStampMap;
    }

    public long getMaxsize() {
        return maxsize;
    }

    public void setMaxsize(long maxsize) {
        this.maxsize = maxsize;
    }

    public long getObjectLifeTime() {
        return objectLifeTime;
    }

    public void setObjectLifeTime(long objectLifeTime) {
        this.objectLifeTime = objectLifeTime;
    }

    public boolean isLock() {
        return lock;
    }

    public void setLock(boolean lock) {
        this.lock = lock;
    }

    /**
     * This method is used to retrive the object from cache
     * @param key
     * @return
     */
    public Object get(String key){
        return this.getCacheMap().get(key);
    }

    /**
     * this method is used for putting an object in cache
     * @param key
     * @param object
     */
    public void put(String key, Object object){
        //get the curr date
        Date date = new Date(System.currentTimeMillis());
        //set object in cacheMap
        this.getCacheMap().put(key,object);
        //put timestamp in cache
        this.getTimeStampMap().put(key, date);
    }

    public void delete(String key){
        this.getCacheMap().remove(key);
        this.getTimeStampMap().remove(key);
    }

    public void clearAll(){
        this.setCacheMap(new  LinkedHashMap<String, Object>());
        this.setTimeStampMap(new  LinkedHashMap<String, Date>());
    }

    /**
     * remove last 2 entries
     * not worried about object life time
     * this is just an example
     */
    public void resize(){
        System.out.println("inside resize");
        long size = this.getCacheMap().size();
        System.out.println("size + " + size);
        if(size == this.getMaxsize()){
            System.out.println("max size has reached");
            Map.Entry<String, Date> firstEntry = this.getTimeStampMap().entrySet().iterator().next();
            System.out.println("removing : " + firstEntry.getKey() + " value : " + firstEntry.getValue());

            this.timeStampMap.remove(firstEntry.getKey());

            Map.Entry<String, Object> firstCEntry = this.getCacheMap().entrySet().iterator().next();
            System.out.println("removing : " + firstCEntry.getKey() + " value : " + firstCEntry.getValue());
            this.cacheMap.remove(firstCEntry.getKey());
        }
        System.out.println("leaving resize with size : " + this.getCacheMap().size());
    }
}