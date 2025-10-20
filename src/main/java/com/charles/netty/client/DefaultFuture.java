package com.charles.netty.client;

import com.charles.netty.util.Response;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DefaultFuture {
    public final static ConcurrentHashMap<Long, DefaultFuture> allDefaultFuture = new ConcurrentHashMap<Long, DefaultFuture>();

    final Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();
    private Response response;

    public DefaultFuture(ClientRequest request) {
        allDefaultFuture.put(request.getId(), this);
    }
    // waiting for response
    public Response get() {
        lock.lock();
        try {
            while (!done()) {
                condition.await();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return response;
    }
    // receive response
    public static void receive(Response response){
        DefaultFuture df = allDefaultFuture.get(response.getId());
        if (df != null) {
            Lock lock = df.lock;
            lock.lock();
            try {
                df.setResponse(response);
                df.condition.signal();
                allDefaultFuture.remove(df);
            }catch (Exception e){
                e.printStackTrace();
            }
            finally {
                lock.unlock();
            }


        }
    }

    public Response getResponse() {
        return response;
    }



    public void setResponse(Response response) {
        this.response = response;
        lock.lock();
        try {
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }

    private boolean done() {
        return this.response != null;
    }
}
