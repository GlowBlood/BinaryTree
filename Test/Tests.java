import org.junit.Test;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class Tests {

    private boolean assertListContains(List list, List otherList){
        for (int i = 0; i < list.size(); i++){
            if (!otherList.contains(list.get(i)) || !list.contains(otherList.get(i))){
                return false;
            }
        }
        return true;
    }


    @Test
    public void assertListContainsTest() {
        assertTrue(assertListContains(Arrays.asList(1, 2, 3, 4, 5, 6), Arrays.asList(6, 5, 4, 3, 2, 1)));
        assertFalse(assertListContains(Arrays.asList(1, 3, 4, 5, 6), Arrays.asList(6, 5, 4, 3, 2, 1)));
        assertFalse(assertListContains(Arrays.asList(1, 2, 4), Arrays.asList(6, 4, 2)));
        assertTrue(assertListContains(Arrays.asList(1, 2, 4), Arrays.asList(1, 4, 2)));
    }

    private boolean createTest(BinaryTree binaryTree, List expectedList) {
        List list = new ArrayList();
        BinaryTree.BinaryTreeIterator binaryTreeIterator = (BinaryTree.BinaryTreeIterator) binaryTree.iterator();
        while (binaryTreeIterator.hasNext()) {
            list.add(binaryTreeIterator.next());
        }
        return assertListContains(list, expectedList);
    }

    @Test public void remove(){
        BinaryTree binaryTreeTest1 = new BinaryTree();
        binaryTreeTest1.add(5);
        binaryTreeTest1.add(2);
        binaryTreeTest1.add(7);
        binaryTreeTest1.add(1);
        binaryTreeTest1.add(4);
        binaryTreeTest1.add(0);
        binaryTreeTest1.add(3);
        binaryTreeTest1.remove(2);
        assertFalse(createTest(binaryTreeTest1, Arrays.asList(5, 2, 1, 0, 4, 3, 7)));
        assertTrue(createTest(binaryTreeTest1, Arrays.asList(5, 1, 0, 4, 3, 7)));

        BinaryTree binaryTreeTest2 = new BinaryTree();
        binaryTreeTest2.add(0);
        binaryTreeTest2.add(1);
        binaryTreeTest2.add(2);
        binaryTreeTest2.add(3);
        assertTrue(binaryTreeTest2.remove(0));
        assertFalse(createTest(binaryTreeTest2, Arrays.asList(0, 2, 1, 3)));
        assertTrue(createTest(binaryTreeTest2, Arrays.asList(2, 1, 3)));

        BinaryTree binaryTreeTest3 = new BinaryTree();
        binaryTreeTest3.add(48);
        binaryTreeTest3.add(52);
        binaryTreeTest3.add(41);
        binaryTreeTest3.add(39);
        binaryTreeTest3.add(47);
        binaryTreeTest3.add(49);
        binaryTreeTest3.add(54);
        assertFalse(binaryTreeTest3.remove(43));
        assertTrue(createTest(binaryTreeTest3, Arrays.asList(39, 41, 47, 48, 49, 52, 54)));
        assertTrue(createTest(binaryTreeTest3, Arrays.asList(39, 41, 47, 48, 54, 52, 49)));
        assertFalse(createTest(binaryTreeTest3, Arrays.asList(41, 47, 48, 54, 52, 49)));
        assertTrue(binaryTreeTest3.remove(41));
        assertTrue(createTest(binaryTreeTest3, Arrays.asList(39, 47, 48, 49, 52, 54)));
        assertTrue(binaryTreeTest3.remove(49));
        assertTrue(createTest(binaryTreeTest3, Arrays.asList(39, 47, 48, 52, 54)));
        assertFalse(binaryTreeTest3.remove(99));
    }


}
/*
    @Test
    void assertListContains(){
        assertTrue(assertListContains(Arrays.asList(1,2,3,4,5,6), Arrays.asList(6,5,4,3,2,1)));

    }

    private boolean createTest(BinaryTree binaryTree, List expectedList){
        List list = new ArrayList();
        BinaryTree.BinaryTreeIterator binaryTreeIterator = (BinaryTree.BinaryTreeIterator) binaryTree.iterator();
        while (binaryTreeIterator.hasNext()){
            list.add(binaryTreeIterator.next());
        }
        return assertListContains(list, expectedList);
    }

    @Test
    void remove(){
        BinaryTree binaryTreetest = new BinaryTree();
        binaryTreetest.add(1);
        binaryTreetest.add(3);
        binaryTreetest.add(9);
        binaryTreetest.add(5);
        binaryTreetest.add(7);
        binaryTreetest.add(8);
        binaryTreetest.add(4);
        binaryTreetest.add(2);
        binaryTreetest.add(9);
        binaryTreetest.add(7);
        binaryTreetest.add(6);
        binaryTreetest.remove(2);

    }
}
*/