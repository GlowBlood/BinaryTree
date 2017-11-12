import org.junit.Test;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class Tests {

    private boolean assertListContains(List list, List otherList) {
        for (int i = 0; i < list.size(); i++) {
            if (!otherList.contains(list.get(i)) || !list.contains(otherList.get(i))) {
                return false;
            }
        }
        return true;
    }


    @Test
    public void assertListContainsTest() {
        assertTrue(assertListContains(Arrays.asList(1, 2, 3, 4, 5, 6), Arrays.asList(6, 5, 4, 3, 2, 1)));
    }

    private boolean createTest(BinaryTree binaryTree, List expectedList) {
        List list = new ArrayList();
        BinaryTree.BinaryTreeIterator binaryTreeIterator = (BinaryTree.BinaryTreeIterator) binaryTree.iterator();
        while (binaryTreeIterator.hasNext()) {
            list.add(binaryTreeIterator.next());
        }
        return assertListContains(list, expectedList);
    }

    @Test
    public void remove() {
        BinaryTree binaryTreeTest1 = new BinaryTree();

    }

}

