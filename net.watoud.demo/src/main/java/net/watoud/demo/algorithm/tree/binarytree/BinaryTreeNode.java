/**
 * 
 */
package net.watoud.demo.algorithm.tree.binarytree;

/**
 * @author xudong
 *
 */
public class BinaryTreeNode<T>
{
	/**
	 * 节点中存储的数据
	 */
	private T data;

	/**
	 * 左指针
	 */
	private BinaryTreeNode<T> left;

	/**
	 * 右指针
	 */
	private BinaryTreeNode<T> right;

	public T getData()
	{
		return data;
	}

	public void setData(T data)
	{
		this.data = data;
	}

	public BinaryTreeNode<T> getLeft()
	{
		return left;
	}

	public void setLeft(BinaryTreeNode<T> left)
	{
		this.left = left;
	}

	public BinaryTreeNode<T> getRight()
	{
		return right;
	}

	public void setRight(BinaryTreeNode<T> right)
	{
		this.right = right;
	}
}
