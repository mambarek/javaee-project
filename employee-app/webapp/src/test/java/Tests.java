import org.junit.Test;

public class Tests {

    @Test
    public void test(){
        for(int i=0; i<5; i++){
            System.out.println("found i = " + i);
        }

        System.out.println(" ");
        System.out.println(" ");

        for(int j=0; j<5; ++j){
            System.out.println("found j = " + j);
        }
    }
}
