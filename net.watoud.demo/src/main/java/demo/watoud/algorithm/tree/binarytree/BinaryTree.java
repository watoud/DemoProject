/**
 * 
 */
package demo.watoud.algorithm.tree.binarytree;

import java.util.Iterator;
import java.util.List;

/**
 * @author xudong
 *
 */
public class BinaryTree<T>
{
	/**
	 * ���ڵ�
	 */
	private BinaryTreeNode<T> root;

	public BinaryTree()
	{
		this.root = null;
	}

	public BinaryTree(BinaryTreeNode<T> root)
	{
		this.root = root;
	}

	public BinaryTreeNode<T> getRoot()
	{
		return root;
	}

	public void setRoot(BinaryTreeNode<T> root)
	{
		this.root = root;
	}

	/**
	 * ��ָ����˳�򷵻ر������ĵ�һ�����
	 * 
	 * @param value
	 * @param order
	 * @return
	 */
	BinaryTreeNode<T> findSingle(T value, TreeTraversalOrder order)
	{
		// TODO
		return null;
	}

	/**
	 * ���صĽ������֤�κ�˳��
	 * 
	 * @param value
	 * @return
	 */
	List<BinaryTreeNode<T>> findAlls(T value)
	{
		// TODO
		return null;
	}

	/**
	 * ǰ�������
	 * 
	 * @author xudong
	 *
	 */
	class PreOrderIterator implements Iterator<BinaryTreeNode<T>>
	{

		@Override
		public boolean hasNext()
		{
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public BinaryTreeNode<T> next()
		{
			// TODO Auto-generated method stub
			return null;
		}
	}

	/**
	 * ���������
	 * 
	 * @author xudong
	 *
	 */
	class InOrderIterator implements Iterator<BinaryTreeNode<T>>
	{

		@Override
		public boolean hasNext()
		{
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public BinaryTreeNode<T> next()
		{
			// TODO Auto-generated method stub
			return null;
		}

	}
}
