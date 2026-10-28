package com.java.traning.threading;

// Holds the per-thread request context. This is the only place the ThreadLocal lives.
class RequestContext {
    private static final ThreadLocal<String> CURRENT_USER = new ThreadLocal<>();

    static void setUser(String user) {
        CURRENT_USER.set(user);
    }

//    Each Thread object has its own internal map (ThreadLocalMap),
//    and set() stores an entry in the current thread’s map,
//    keyed by the ThreadLocal object.
//    get() looks up the current thread’s map with the same key.

    static String getUser() {
        return CURRENT_USER.get();
    }

    static void clear() {
        CURRENT_USER.remove();   // important when the thread is reused
    }
}

// Lowest layer: reads the user from the context, not from a parameter
class Repository {
    void findOrders() {
        System.out.println("[" + Thread.currentThread().getName() + "] "
                + "Repository: orders query for user " + RequestContext.getUser());
    }
}

// Middle layer: has no knowledge of the user
class Service {
    private final Repository repository = new Repository();

    void placeOrder() {
        System.out.println("[" + Thread.currentThread().getName() + "] Service: placing order");
        repository.findOrders();
    }
}

// Top layer: has no knowledge of the user either
class Controller {
    private final Service service = new Service();

    void handleGetOrders() {
        System.out.println("[" + Thread.currentThread().getName() + "] Controller: handling request");
        service.placeOrder();
    }
}

// The unit of work given to a thread. Each instance represents one request.
class RequestHandler implements Runnable {
    private final String user;
    private final Controller controller = new Controller();

    RequestHandler(String user) {
        this.user = user;
    }

    @Override
    public void run() {
        RequestContext.setUser(user);    // set once, at the entry point
        try {
            controller.handleGetOrders();
        } finally {
            RequestContext.clear();      // clean up before the thread is reused
        }
    }
}

public class ThreadLocalChainDemo {

    public static void main(String[] args) throws InterruptedException {
        // Each RequestHandler is a Runnable, and each Thread is a separate thread
        Thread t1 = new Thread(new RequestHandler("asha"), "request-1");
        Thread t2 = new Thread(new RequestHandler("ravi"), "request-2");

        t1.start();
        t2.start();
        t1.join();
        t2.join();
    }
}
