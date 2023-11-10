package jtaskflow;

import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.Callable;

import org.junit.jupiter.api.Test;

import jtaskflow.core.JTFExecutor;
import jtaskflow.core.JTFSubFlow;
import jtaskflow.core.JTFTask;
import jtaskflow.core.JTaskFlow;

public class BasicTest1 {
    @Test
    public void test1() {
        System.out.println("Test 1");
        assertTrue(true);
        assertFalse(false);
    }

    @Test
    public void testBasic() {

        JTFTask<Void> A = new JTFTask<Void>("A",new Callable<Void>(){
            public Void call(){
                System.out.println("Task A");
                return null;
            }
        });
        JTFTask<Void> B = new JTFTask<Void>("B",new Callable<Void>(){
            public Void call(){
                System.out.println("Task B");
                return null;
            }
        });

        JTaskFlow tf = new JTaskFlow().addNode(A).addNode(B);
        A.precede(B);

        JTFExecutor.execute(tf);
        
        assertTrue(true);
    }


    @Test
    public void testBasic2() {

        JTFTask<Void> A = new JTFTask<Void>("A",new Callable<Void>(){
            public Void call(){
                System.out.println("Task A");
                return null;
            }
        });
        JTFTask<Void> B = new JTFTask<Void>("B",new Callable<Void>(){
            public Void call(){
                System.out.println("Task B");
                return null;
            }
        });
        JTFTask<Void> C = new JTFTask<Void>("C",new Callable<Void>(){
            public Void call(){
                System.out.println("Task C");
                return null;
            }
        });
        JTFTask<Void> D = new JTFTask<Void>("D",new Callable<Void>(){
            public Void call(){
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                System.out.println("Task D");
                return null;
            }
        });
        JTFTask<Void> E = new JTFTask<Void>("E",new Callable<Void>(){
            public Void call(){
                System.out.println("Task E");
                return null;
            }
        });
        JTFTask<Void> F = new JTFTask<Void>("F",new Callable<Void>(){
            public Void call(){
                System.out.println("Task F");
                return null;
            }
        });
        JTFTask<Void> G = new JTFTask<Void>("G",new Callable<Void>(){
            public Void call(){
                System.out.println("Task G");
                return null;
            }
        });
        /*
         *          -> C
         *   A -> B -> D -> G
         *          -> E
         *   F ----------->               
         */

        JTaskFlow tf = new JTaskFlow().addNode(A).addNode(B).addNode(C).addNode(D).addNode(E).addNode(F).addNode(G);
        A.precede(B);
        B.precede(C).precede(D).precede(E);
        G.succeed(C).succeed(D).succeed(E).succeed(F);


        JTFExecutor.execute(tf);
        
        assertTrue(true);
    }

    @Test
    public void testBasic3() {

        JTFTask<Void> A = new JTFTask<Void>("A",new Callable<Void>(){
            public Void call(){
                System.out.println("Task A");
                return null;
            }
        });
        JTFTask<Void> B = new JTFTask<Void>("B",new Callable<Void>(){
            public Void call(){
                System.out.println("Task B");
                return null;
            }
        });
        JTFTask<Void> C = new JTFTask<Void>("C",new Callable<Void>(){
            public Void call(){
                System.out.println("Task C");
                return null;
            }
        });
        JTFTask<Void> D = new JTFTask<Void>("D",new Callable<Void>(){
            public Void call(){
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                System.out.println("Task D");
                return null;
            }
        });
        JTFTask<Void> E = new JTFTask<Void>("E",new Callable<Void>(){
            public Void call(){
                System.out.println("Task E");
                return null;
            }
        });
        JTFTask<Void> F = new JTFTask<Void>("F",new Callable<Void>(){
            public Void call(){
                System.out.println("Task F");
                return null;
            }
        });
        JTFTask<Void> G = new JTFTask<Void>("G",new Callable<Void>(){
            public Void call(){
                System.out.println("Task G");
                return null;
            }
        });
        /*
         *          -> C
         *   A -> B -> D -> G
         *          -> E
         *   F ----------->               
         */
        JTFSubFlow sf = new JTFSubFlow("CD").addNode(C).addNode(D);
        JTaskFlow tf = new JTaskFlow().addNode(A).addNode(B).addNode(sf).addNode(E).addNode(F).addNode(G);
        A.precede(B);
        B.precede(sf).precede(E);
        G.succeed(sf).succeed(E).succeed(F);


        JTFExecutor.execute(tf);
        
        assertTrue(true);
    }
}
