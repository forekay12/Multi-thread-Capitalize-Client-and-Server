import java.io.IOException;
import java.io.PrintWriter;

// Kayla Foremski
// Hunter Rich

public class SharedQueue {

	private class Node {
		private JobObject data;
		private Node next;
		public Node(JobObject elem) {
			this.data = elem;
			this.next = null;
		}
	} //end of Node class

	private Node head, tail;  // the head and tail nodes
	private int size; 
	private int maxQueueSize;


	public SharedQueue(int maxQueueSize) {
		this.head = null; 
		this.tail = null; 
		this.size = 0;
		this.maxQueueSize = maxQueueSize;

	}

	public synchronized void enqueue(JobObject val) {

		// changed from wait to if statement
		if(this.size >= this.maxQueueSize) {
			try {
				(new PrintWriter(val.getSocket().getOutputStream(), true)).println("Server busy try again later.");
				val.getSocket().close();
				System.out.println("Server Rejected Connetion with CLient #" + val.getClientNumber() + "\n");
				return;
			} catch (IOException e) { }
		}

		Node node = new Node(val);
		if (size == 0)
			head = node; // edge case of a previously empty queue
		else
			tail.next = node;  // add node at the tail of the list

		tail = node;          // update the reference to the tail node
		size++;   // tail always points to the last inserted item.

		notifyAll();
	}

	public synchronized JobObject dequeue() {

		while ( isEmpty() ) {
			try {
				wait();
			}
			catch ( InterruptedException e ) {
				return new JobObject(null, 0);
			}
		}

		JobObject val = head.data;
		head = head.next;   //head always points to the item that is ready to be dequeued next.
		size--;
		if (size == 0)
			tail = null; // the queue is now empty, why not set head = null also?

		notifyAll();
		return val;
	}

	public synchronized boolean isEmpty() {   
		return this.size == 0;
	}

	public synchronized int getSize() {
		return this.size;
	}

}

