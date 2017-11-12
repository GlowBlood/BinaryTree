import javafx.util.Pair;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.*;


// Attention: comparable supported but comparator is not
@SuppressWarnings("WeakerAccess")
public class BinaryTree<T extends Comparable<T>> extends AbstractSet<T> implements SortedSet<T> {

    private static class Node<T> {
        T value;

        Node<T> left = null;

        Node<T> right = null;

        Node(T value) {
            this.value = value;
        }
    }

    private Node<T> root = null;

    private int size = 0;

    @Override
    public boolean add(T t) {
        Node<T> closest = find(t);
        int comparison = closest == null ? -1 : t.compareTo(closest.value);
        if (comparison == 0) {
            return false;
        }
        Node<T> newNode = new Node<>(t);
        if (closest == null) {
            root = newNode;
        }
        else if (comparison < 0) {
            assert closest.left == null;
            closest.left = newNode;
        }
        else {
            assert closest.right == null;
            closest.right = newNode;
        }
        size++;
        return true;
    }

    boolean checkInvariant() {
        return root == null || checkInvariant(root);
    }

    private boolean removeFromParent(Node<T> start, T value) {
        int comparison = value.compareTo(start.value);
        Integer comparisonOfLeft = start.left == null ? null : value.compareTo(start.left.value);
        Integer comparisonOfRight = start.right == null ? null : value.compareTo(start.right.value);
        if ((comparisonOfLeft != null && comparisonOfLeft == 0)){
            start.left = null;
            return true;
        } else if (comparisonOfRight != null && comparisonOfRight == 0) {
            start.right = null;
            return true;
        } else if (comparison < 0) {
            return start.left != null && removeFromParent(start.left, value);
        } else {
            return start.right != null && removeFromParent(start.right, value);
        }
    }

    @Override
    public boolean remove(Object o) {
        //решение в лоб, с затратой кучи памяти
        BinaryTree helpBinaryTree = new BinaryTree();
        BinaryTreeIterator helpIterator = new BinaryTreeIterator();
        while (helpIterator.hasNext()){
            if (helpIterator != o) helpBinaryTree.add(helpIterator.next());
        }
        if (size - helpBinaryTree.size() == 1){
            this.root = helpBinaryTree.root;
            size--;
            return true;
        }
        else return false;
    }

    @Override
    public boolean contains(Object o) {
        @SuppressWarnings("unchecked")
        T t = (T) o;
        Node<T> closest = find(t);
        return closest != null && t.compareTo(closest.value) == 0;
    }

    private Node<T> find(T value) {
        if (root == null) return null;
        return find(root, value);
    }

    private Node<T> find(Node<T> start, T value) {
        int comparison = value.compareTo(start.value);
        if (comparison == 0) {
            return start;
        }
        else if (comparison < 0) {
            if (start.left == null) return start;
            return find(start.left, value);
        }
        else {
            if (start.right == null) return start;
            return find(start.right, value);
        }
    }

    public class BinaryTreeIterator implements Iterator<T> {

        private Node<T> current = null;

        private BinaryTreeIterator() {}


        //вспомогательные переменные и методы
        private List<Pair> helpList = new ArrayList<>();
        private boolean locked = false;
        private int countLonelyNodes = 0;

        private class Pair{
            private boolean twoKids;
            private Node<T> node;

            private Pair(boolean twoKids, Node<T> node){
                this.twoKids = twoKids;
                this.node = node;
            }
        }

        private Pair findLast(){
            Pair lastPair = null;
            int way = helpList.size();
            for (int i = 0; i < way; i++){
                if (helpList.get(i).twoKids){
                    lastPair = helpList.get(i);
                    countLonelyNodes = i;
                }
            }
            return lastPair;
        }

        private void cutSingleBranch(){
            helpList = helpList.subList(0, countLonelyNodes + 1);
            helpList.get(countLonelyNodes).twoKids = false;
        }
        //конец вспомогательных переменных и методов


        private Node<T> findNext() {
            if (!locked) {
                if (root == null) return null;
                current = root;
                locked = true;
                return current;
            }

            if (current.left != null && current.right != null){//has both
                helpList.add(new Pair(true, current));
                current = current.left;
                return current;
            }

            if (current.left != null){ //has only left
                helpList.add(new Pair(false, current));
                current = current.left;
                return current;
            }

            if (current.right != null){ //has only right
                helpList.add(new Pair(false, current));
                current = current.right;
                return current;
            }

            else {
                if (findLast() != null){
                    current = findLast().node;
                    cutSingleBranch();
                    if (current.right != null){
                        current = current.right;
                        return current;
                    }
                }
                current = null;
                locked = false;
                helpList = new ArrayList<>();
                return null;
            }
        }

        @Override
        public boolean hasNext() {
            return findNext() != null;
        }

        @Override
        public T next() {
            current = findNext();
            if (current == null) throw new NoSuchElementException();
            return current.value;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new BinaryTreeIterator();
    }

    @Override
    public int size() {
        return size;
    }


    @Nullable
    @Override
    public Comparator<? super T> comparator() {
        return null;
    }

    @NotNull
    @Override
    public SortedSet<T> subSet(T fromElement, T toElement) {
        throw new UnsupportedOperationException();
    }

    @NotNull
    @Override
    public SortedSet<T> headSet(T toElement) {
        throw new UnsupportedOperationException();
    }

    @NotNull
    @Override
    public SortedSet<T> tailSet(T fromElement) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T first() {
        if (root == null) throw new NoSuchElementException();
        Node<T> current = root;
        while (current.left != null) {
            current = current.left;
        }
        return current.value;
    }

    @Override
    public T last() {
        if (root == null) throw new NoSuchElementException();
        Node<T> current = root;
        while (current.right != null) {
            current = current.right;
        }
        return current.value;
    }
}