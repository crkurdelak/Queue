import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A queue of elements of type E.
 *
 * @param <E> the type of elements in this list
 *
 * @author ckurdelak20@georgefox.edu
 */
public class Queue<E> implements Iterable<E> {

    private QueueNode<E> _head;
    private QueueNode<E> _tail;
    private int _depth;


    /**
     * Creates a new Queue.
     */
    public Queue() {
        _head = null;
        _tail = null;
    }


    /**
     * Adds the specified element to the end of this Queue.
     *
     * @param element the element to be enqueued
     * @return true if this collection has changed as a result of the call
     */
    public boolean enqueue(E element) {
        QueueNode<E> newNode;
        if (isEmpty()) {
            newNode = new QueueNode<>(element);
            _head = newNode;
            _tail = newNode;
        }
        else {
            newNode = new QueueNode<>(element);
            newNode.setPrevious(_tail);
            _tail.setNext(newNode);
            _tail = newNode;
        }
        _depth ++;
        return true;
    }


    /**
     * Enqueues all given elements.
     *
     * @param elements an iterable collection of elements to enqueue
     */
    public void enqueueAll(Iterable<E> elements) {
        for (E element : elements) {
            this.enqueue(element);
        }
    }


    /**
     * Returns the value at the head of this Queue without dequeueing it.
     *
     * @return the value at the head of this Queue.
     * @throws NoSuchElementException if the Queue is empty
     */
    public E head() {
        if (!isEmpty()) {
            return _head.getValue();
        }
        else {
            throw new NoSuchElementException();
        }
    }


    /**
     * Removes and returns the head of this Queue.
     *
     * @return the value of the head of this Queue
     * @throws NoSuchElementException if the Queue is empty
     */
    public E dequeue() {
        if (!isEmpty()) {
            QueueNode<E> oldHead = _head;
            _head = oldHead.getNext();

            _depth --;
            return oldHead.getValue();
        }
        else {
            throw new NoSuchElementException(); // TODO see if this is right exception to throw
        }
    }


    /**
     * Removes all the elements from this Queue. The Queue will be empty after this call returns.
     */
    public void clear() {
        _head = null;
        _tail = null;
        _depth = 0;
    }


    /**
     * Returns true if this Queue contains no elements.
     *
     * @return true if this Queue contains no elements
     * false if this Queue contains elements
     */
    public boolean isEmpty() {
        return (_depth == 0);
    }


    /**
     * Returns the number of elements in this list.
     *
     * @return the number of elements in this list
     */
    public int depth() {
        return _depth;
    }


    /**
     * Returns a new LinkedListIterator object that iterates from head to tail.
     *
     * @return a new LinkedListIterator object that iterates from head to tail
     */
    public Iterator<E> iterator() {
        return new QueueIterator();
    }


    /**
     * A node that stores a single element in a LinkedList as well as references to its adjacent
     * nodes.
     *
     * @param <E> the type of element stored in this node
     */
    private class QueueNode<E> {

        private E _value;
        private QueueNode<E> _prev;
        private QueueNode<E> _next;

        /**
         * Constructs a new QueueNode.
         */
        public QueueNode() {
            this(null);
        }


        /**
         * Constructs a new QueueNode with the given value.
         *
         * @param value the value stored in this QueueNode
         */
        public QueueNode(E value) {
            this(value, null, null);
        }


        /**
         * Constructs a new QueueNode with the given value.
         *
         * @param value the value stored in this QueueNode
         * @param prev the previous QueueNode
         * @param next the next QueueNode
         */
        public QueueNode(E value, QueueNode<E> prev, QueueNode<E> next) {
            _value = value;
            _prev = prev;
            _next = next;
        }


        /**
         * Returns the value of this QueueNode.
         *
         * @return the value of this QueueNode
         */
        public E getValue() {
            return _value;
        }


        /**
         * Returns the previous QueueNode.
         *
         * @return the previous QueueNode
         */
        public QueueNode<E> getPrevious() {
            return _prev;
        }


        /**
         * Returns the next QueueNode.
         *
         * @return the next QueueNode
         */
        public QueueNode<E> getNext() {
            return _next;
        }


        /**
         * Sets the value of this node to a new value.
         *
         * @param value the new value of this node
         */
        public void setValue(E value) {
            _value = value;
        }


        /**
         * Sets the prev attribute to a new node.
         *
         * @param prev the new previous node
         */
        public void setPrevious(QueueNode<E> prev) {
            _prev = prev;
        }


        /**
         * Sets the next attribute to a new node.
         *
         * @param next the new next node
         */
        public void setNext(QueueNode<E> next) {
            _next = next;
        }
    }


    /**
     * Implements the Iterator<T> interface for the Queue class.
     */
    private class QueueIterator implements Iterator<E> {

        /**
         * Constructs a new QueueIterator object.
         *
         */
        public QueueIterator() {
            // No internal state is needed because the top is always consumed during iteration.
        }

        /**
         * Returns true if there are more elements in this iteration, else returns false.
         *
         * @return true if there are more elements in this iteration
         * else return false
         */
        public boolean hasNext() {
            return (!isEmpty());
        }


        /**
         * Returns the next element in this iteration.
         *
         * @return the next element in this iteration
         * @throws NoSuchElementException if the iteration has no more elements
         */
        public E next() {
            if (hasNext()) {
                return dequeue();
            }
            else {
                throw new NoSuchElementException();
            }
        }
    }
}

