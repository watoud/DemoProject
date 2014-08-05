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
	 * 根节点
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
	 * 按指定的顺序返回遍历到的第一个结果
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
	 * 返回的结果不保证任何顺序
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
	 * 前序迭代器
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
	 * 中序迭代器
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
