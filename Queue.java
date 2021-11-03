import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A list of elements of type E.
 *
 * @param <E> the type of elements in this list
 *
 * @author ckurdelak20@georgefox.edu
 */
public class Queue<E> implements Iterable<E> {
    // TODO implement Queue
    // TODO remove all index-based stuff
    // TODO throw correct exception (NoSuchElementException)

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
     * @param element the element to be appended to this Queue
     * @return true if this collection has changed as a result of the call
     */
    public boolean add(E element) {
        // TODO change to enqueue @ tail
        LinkedListNode<E> newNode;
        if (this.size() == 0) {
            newNode = new LinkedListNode<>(element);
            _head = newNode;
            _tail = newNode;
        }
        else {
            newNode = new LinkedListNode<>(element);
            newNode.setPrevious(_tail);
            _tail.setNext(newNode);
            _tail = newNode;
        }
        _size++;
        _modCount++;

        return true;
    }


    // TODO implement enqueueAll
    // for each element in elements, enqueue element


    // TODO implement head() method

    /**
     * Returns the value at the head of this Queue without dequeueing it.
     *
     * @return the value at the head of this Queue.
     */
    public E head() {
        // returns head
        return _head.getValue();
    }


    /**
     * Removes and returns the head of this Queue.
     *
     * @return the value of the head of this Queue
     */
    public E dequeue() {
        // TODO change to dequeue @ head
        if (this.isValidIndex(index) && !isEmpty()) {
            LinkedListNode<E> oldNode;
            LinkedListNode<E> prevNode;
            LinkedListNode<E> nextNode;
            E oldValue;

            if (index == 0) {
                oldNode = _head;
                oldValue = oldNode.getValue();
                if (this.size() > 1) {
                    nextNode = oldNode.getNext();

                    nextNode.setPrevious(null);
                    _head = nextNode;
                }
                else {
                    _head = null;
                }
            }
            else if (index == size() - 1) {
                oldNode = _tail;
                oldValue = oldNode.getValue();
                prevNode = oldNode.getPrevious();

                prevNode.setNext(null);
                _tail = prevNode;
            }
            else {
                oldNode = this.seek(index);
                oldValue = oldNode.getValue();
                prevNode = oldNode.getPrevious();
                nextNode = oldNode.getNext();

                prevNode.setNext(nextNode);
                nextNode.setPrevious(prevNode);
            }

            oldNode.setPrevious(null);
            oldNode.setNext(null);
            oldNode.setValue(null);

            _size--;
            _modCount++;

            return oldValue;
        }
        else {
            throw new IndexOutOfBoundsException();
        }
    }


    /**
     * Removes all the elements from this Queue. The Queue will be empty after this call returns.
     */
    public void clear() {
        if (_head != null && _tail != null) {
            _head = null;
            _tail = null;
            _depth = 0;
        }
    }


    /**
     * Returns true if this list contains no elements.
     *
     * @return true if this list contains no elements
     * false if this list contains elements
     */
    public boolean isEmpty() {
        return (_size == 0);
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

        E _value;
        QueueNode<E> _prev;
        QueueNode<E> _next;

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

        // TODO iterator dequeues repeatedly @ head

        private QueueNode<E> _currentNode;


        /**
         * Constructs a new QueueIterator object.
         */
        public LinkedListIterator(boolean reverse) {
            if (reverse) {
                // The index of the last element is size - 1 because indexing starts at 0.
                _currentIndex = size() - 1;
                _currentNode = _tail;
            }
            else {
                _currentIndex = 0;
                _currentNode = _head;
            }
            _reverse = reverse;
            _modCountCopy = _modCount;
        }

        /**
         * Returns true if the current head has a next, else returns false.
         *
         * @return true if the current head has a next
         * else return false
         */
        public boolean hasNext() {
            if (_modCountCopy == _modCount) {
                if (! _reverse) {
                    return _currentIndex < _size;
                }
                else {
                    return _currentIndex >= 0;
                }
            }
            else {
                throw new ConcurrentModificationException();
            }
        }


        /**
         * Returns the next element in this iteration.
         *
         * @return the next element in this iteration
         * @throws NoSuchElementException if the iteration has no more elements
         */
        public E next() {
            if (_modCountCopy == _modCount) {
                if (hasNext()) {
                    E item = get(_currentIndex);
                    if (item == null) {
                        throw new NoSuchElementException();
                    }
                    if (!_reverse) {
                        _currentNode = _currentNode.getNext();
                        _currentIndex++;
                    } else {
                        _currentNode = _currentNode.getPrevious();
                        _currentIndex--;
                    }
                    return item;
                }
                else {
                    throw new NoSuchElementException();
                }
            }
            else {
                throw new ConcurrentModificationException();
            }
        }
    }
}

