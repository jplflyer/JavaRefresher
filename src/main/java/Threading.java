/**
 * This is where I'll do some threading examples. I'm going to build this up.
 */
public class Threading {
    /**
     * An example of extending Thread. Implement run().
     */
    public class MyThread extends Thread {
        public void run() {
            System.out.println("MyThread started");
        }
    }

    /**
     * An example of implementing Runnable. Implement run().
     */
    public class MyRunnable implements Runnable {
        public void run() {
            System.out.println("MyRunnable started");
        }
    }

    /**
     * Entrypoint.
     */
    public static void main(String [] args) {
        Threading simple = new Threading();
        simple.startingThreads();
        simple.demonstrateConcurrency();
    }

    /**
     * This starts threads in a variety of ways.
     */
    public void startingThreads() {
        // Extend thread.
        MyThread myThread = new MyThread();
        myThread.start();

        // Implement runnable
        MyRunnable myRunnable = new MyRunnable();
        Thread fromRunnable = new Thread(myRunnable);
        fromRunnable.start();

        // Implement runnable inline.
        Thread fromAnonymousRunnable = new Thread(new Runnable() {
            public void run() {
                System.out.println("Anonymous Runnable running");
            }
        });
        fromAnonymousRunnable.start();

        // This example uses a lambda.
        Thread lambdaThread = new Thread(() -> {
            System.out.println("Hello from my lambda");
        });
        lambdaThread.start();

        try {
            myThread.join();
            fromRunnable.join();
            fromAnonymousRunnable.join();
            lambdaThread.join();
        }
        catch (InterruptedException e) {
            System.out.println("Interrupted: " + e.getMessage());
        }
    }

    /**
     * I'm going to show how to use synchronization.
     *
     * Java does not have distinct mutexes and condition variables.
     * Instead, they're built into Object. You can synchronize on
     * any object (such as this).
     *
     * So the equivalent of this C++ code:
     *
     *      std::mutex m;
     *      {
     *          std::unique_lock<std::mutex> lock(m);
     *          ... do stuff
     *      }
     *
     * Might be this:
     *      synchronized (this) { ... do stuff }
     *
     * And you can call wait() and notify() or notifyAll() just like you
     * might use a C++ condition variable. You can do these on yourself (this)
     * or you can declare different locking variables.
     */
    public void demonstrateConcurrency() {
        Thread mainThread = new Thread(() -> {
            syncMain();
        });
        mainThread.start();

        Thread watcherThread = new Thread(() -> {
            syncWatcher();
        });
        watcherThread.start();

        try {
            mainThread.join();
            watcherThread.join();
        } catch (InterruptedException e) {
            System.out.println("Interrupted: " + e.getMessage());
        }
    }

    private void syncMain() {
        System.out.println("syncMain start.");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // Don't care.
        }
        syncStop();
    }

    /**
     * Watches what syncMain() is doing.
     */
    private synchronized void syncWatcher() {
        java.time.LocalTime startTime = java.time.LocalTime.now();
        System.out.println("syncWatcher start.");
        while (syncRunning) {
            trapWait();
        }
        java.time.LocalTime endTime = java.time.LocalTime.now();
        var foo = startTime.until(endTime, java.time.temporal.ChronoUnit.MILLIS);

        System.out.println("syncWatcher done after " + foo + " milliseconds.");
    }

    private void trapWait() {
        try {
            wait();
        }
        catch (InterruptedException e) {
            // Don't care.
        }
    }

    private synchronized void syncStop() {
        syncRunning = false;
        notifyAll();
    }

    private boolean syncRunning = true;
}
