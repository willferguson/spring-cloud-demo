package burrito.fillingservice.client;

import static org.junit.jupiter.api.Assertions.assertEquals;

//@SpringBootTest(classes = FillingServiceApplication.class)
//@EnableHystrix
public class CollapsingTest {

    //@Autowired
    MeatClient stuffClient;


    //@Test
    public void test() {
//
//
//        MeatClient spiedStuffClient = Mockito.spy(stuffClient);
//
//        Executor executor = Executors.newFixedThreadPool(5);
//
//
//        executor.execute(() -> {
//            HystrixRequestContext context = HystrixRequestContext.initializeContext();
//            stuffClient.getStuff(1);
//
//        });
//        executor.execute(() -> {
//            HystrixRequestContext context = HystrixRequestContext.initializeContext();
//            stuffClient.getStuff(2);
//        });
//        executor.execute(() -> {
//            HystrixRequestContext context = HystrixRequestContext.initializeContext();
//            stuffClient.getStuff(3);
//        });
//        executor.execute(() -> {
//            HystrixRequestContext context = HystrixRequestContext.initializeContext();
//            stuffClient.getStuff(4);
//        });
//        executor.execute(() -> {
//            HystrixRequestContext context = HystrixRequestContext.initializeContext();
//            stuffClient.getStuff(5);
//        });
//
//
//        //Mockito.verify(spiedStuffClient).getLotsOfStuff(Arrays.asList(1, 2, 3, 4, 5));
//
////        assertEquals(1, s1.getSize());
////        assertEquals(2, s2.getSize());
//
//
//       // context.shutdown();

    }
}
