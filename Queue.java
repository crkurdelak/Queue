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
public class LinkedList<E> implements Iterable<E> {

    // for constructing iterators
    private static final boolean REVERSED = true;
    private static final boolean NOT_REVERSED = false;

    private LinkedListNode<E> _head;
    private LinkedListNode<E> _tail;
    private int _size;
    private long _modCount;

    /**
     * Creates a new LinkedList.
     */
    public LinkedList() {
        _head = null;
        _tail = null;
        _size = 0;
        _modCount = 0;
    }


    /**
     * Inserts the specified element at the specified position in this list. Shifts the element
     * currently at that position (if any) and any subsequent elements to the right (adds one to
     * their indices).
     *
     * Maintains constant-time access to head and tail.
     *
     * @param index the index where the element will be inserted
     * @param element the element to be inserted
     * @throws IndexOutOfBoundsException if the specified index is out of range
     */
    public void add(int index, E element) {
        LinkedListNode<E> newNode = new LinkedListNode<>(element);
        LinkedListNode<E> oldNode;
        LinkedListNode<E> prevNode;

        if (index <= this.size() && index >= 0) {
            if (this.size() == 0) {
                _head = newNode;
                _tail = newNode;
            } else {
                if (index == 0) {
                    oldNode = _head; // there is no previous node but there is a next node
                    // (unless size == 1)
                    newNode.setNext(oldNode);
                    oldNode.setPrevious(newNode);
                    _head = newNode;
                } else {
                    if (index == this.size()) {
                        oldNode = _tail; // there is no next node but there is a previous node
                        // (unless size == 1)
                    } else {
                        oldNode = this.seek(index); // there is a previous and a next node bc
                        // oldNode is neither the head nor the tail
                    }

                    prevNode = oldNode.getPrevious();
                    if (prevNode != null) {
                        prevNode.setNext(newNode);
                        newNode.setPrevious(prevNode);
                    }
                    else {
                        newNode.setPrevious(_head);
                    }

                    newNode.setNext(oldNode);
                    oldNode.setPrevious(newNode);

                    if (index == this.size()) {
                        _tail = newNode;
                    }

                }
            }
            _size ++;
            _modCount++;
        }
        else {
            throw new IndexOutOfBoundsException();
        }
    }


    /**
     * Appends the specified element to the end of this list.
     *
     * @param element the element to be appended to this list
     * @return true if this collection has changed as a result of the call
     * @throws IndexOutOfBoundsException if the specified index is out of range
     */
    public boolean add(E element) {
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


    /**
     * Removes all the elements from this list. The list will be empty after this call returns.
     */
    public void clear() {
        // let go of head and tail
        if (_head != null && _tail != null) {
            _head.setValue(null);
            _tail.setValue(null);
            _size = 0;
            _modCount++;
        }
    }


    /**
     * Returns the element at the specified position in this list.
     *
     * Maintains constant-time access to head and tail.
     *
     * @param index the index of the element to return
     * @return the element at the specified position in this list
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    public E get(int index) {
        if (this.isValidIndex(index)) {

        LinkedListNode<E> node;

            if (index == 0) {
                node = _head;
            } else if (index == this.size() - 1) {
                node = _tail;
            } else {
                node = this.seek(index);
            }

            return node.getValue();
        }
        else {
            throw new IndexOutOfBoundsException();
        }
    }


    /**
     * Returns the index of the first occurrence of the specified element in this list,
     * or -1 if this list does not contain the element.
     *
     * More formally, returns the lowest index i such that
     * Objects.equals(o, get(i)), or -1 if there is no such index.
     *
     * @param element the element to search for
     * @return the index of the first occurrence of the specified element in this list,
     * or -1 if this list does not contain the element.
     */
    public int indexOf(E element) {
        Iterator<E> iter = iterator();
        boolean found = false;
        E currentItem;
        int index = 0;

        while (iter.hasNext() && !found) {
            currentItem = iter.next();
            found = (currentItem == element);
            if (!found) {
                index++;
            }
        }

        if (!found) {
            index = -1;
        }

        return index;
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
     * Removes and returns the element from the specified position in this list.
     *
     * Maintains constant-time access to head and tail
     *
     * @param index the index of the element to be removed
     * @return the element that was removed from the list
     * @throws IndexOutOfBoundsException if the specified index is out of range
     */
    public E remove(int index) {
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
     * Replaces the element at the specified position in this list with the specified element.
     *
     * Maintains constant-time access to head and tail.
     *
     * @param index the index of the element to replace
     * @param element the element to be stored at the specified position
     * @return the element previously at the specified position
     * @throws IndexOutOfBoundsException if the specified index is out of range
     */
    public E set(int index, E element) {
        LinkedListNode<E> node;
        E oldValue;
        if (this.isValidIndex(index)) {
            if (index == 0) {
                node = _head;

            }
            else if (index == this.size()) {
                node = _tail;
            }
            else {
                node = this.seek(index);
            }

            oldValue = node.getValue();
            node.setValue(element);
        }
        else {
            throw new IndexOutOfBoundsException();
        }

        return oldValue;
    }


    /**
     * Returns the number of elements in this list.
     *
     * @return the number of elements in this list
     */
    public int size() {
        return _size;
    }


    /**
     * Returns a new LinkedListIterator object that iterates from head to tail.
     *
     * @return a new LinkedListIterator object that iterates from head to tail
     */
    public Iterator<E> iterator() {
        return new LinkedListIterator(NOT_REVERSED);
    }


    /**
     * Returns a new LinkedListIterator object that iterates from tail to head.
     *
     * @return a new LinkedListIterator object that iterates from tail to head
     */
    public Iterator<E> reverseIterator() {
        return new LinkedListIterator(REVERSED);
    }


    /**
     * Checks if the given index is valid.
     *
     * An index is only invalid if it is less than 0 or greater than the size of the list.
     *
     * @param index the index to check
     * @return true if the index is valid; else return false
     */
    private boolean isValidIndex(int index) {
        return (index < this.size() && index >= 0);
    }


    /**
     * Returns the node at the given index.
     *
     * @param index the index to seek to
     * @return the node at the given index
     */
    private LinkedListNode<E> seek(int index) {
        LinkedListNode<E> currentNode = _head;

        for (int i = 0; i < index; i++) {
            currentNode = currentNode.getNext();
        }
        return currentNode;
    }


    /**
     * A node that stores a single element in a LinkedList as well as references to its adjacent
     * nodes.
     *
     * @param <E> the type of element stored in this node
     */
    private class LinkedListNode<E> {

        E _value;
        LinkedListNode<E> _prev;
        LinkedListNode<E> _next;

        /**
         * Constructs a new LinkedListNode.
         */
        public LinkedListNode() {
            this(null);
        }


        /**
         * Constructs a new LinkedListNode with the given value.
         *
         * @param value the value stored in this LinkedListNode
         */
        public LinkedListNode(E value) {
            this(value, null, null);
        }


        /**
         * Constructs a new LinkedListNode with the given value.
         *
         * @param value the value stored in this LinkedListNode
         * @param prev the previous LinkedListNode
         * @param next the next LinkedListNode
         */
        public LinkedListNode(E value, LinkedListNode<E> prev, LinkedListNode<E> next) {
            _value = value;
            _prev = prev;
            _next = next;
        }


        /**
         * Returns the value of this LinkedListNode.
         *
         * @return the value of this LinkedListNode
         */
        public E getValue() {
            return _value;
        }


        /**
         * Returns the previous LinkedListNode.
         *
         * @return the previous LinkedListNode
         */
        public LinkedListNode<E> getPrevious() {
            return _prev;
        }


        /**
         * Returns the next LinkedListNode.
         *
         * @return the next LinkedListNode
         */
        public LinkedListNode<E> getNext() {
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
        public void setPrevious(LinkedListNode<E> prev) {
            _prev = prev;
        }


        /**
         * Sets the next attribute to a new node.
         *
         * @param next the new next node
         */
        public void setNext(LinkedListNode<E> next) {
            _next = next;
        }
    }


    /**
     * Implements the Iterator<T> interface for the LinkedList class.
     *
     * Uses fail-fast iteration.
     */
    private class LinkedListIterator implements Iterator<E> {

        private int _currentIndex;
        private LinkedListNode<E> _currentNode;
        private final boolean _reverse;
        private final long _modCountCopy;

        /**
         * Constructs a new LinkedListIterator object.
         *
         * @param reverse true if this iterator is a reverse iterator, else false
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
         * Returns true if the current index is less than the size of the LinkedList, else
         * returns false.
         *
         * @return true if the current index is less than the size of the LinkedList
         * else return false
         * @throws ConcurrentModificationException if the list has been modified since iteration
         * started
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
         * @throws ConcurrentModificationException if the list has been modified since iteration
         * started
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

