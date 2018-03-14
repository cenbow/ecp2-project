package com.ecp.service.impl.front;

import java.util.ArrayList;
import java.util.List;

import org.apdplat.word.WordSegmenter;
import org.apdplat.word.segmentation.Word;
import org.springframework.stereotype.Service;

import com.ecp.bean.CategoryAttrBean;
import com.ecp.bean.CategoryTreeNode;
import com.ecp.bean.DeletedType;
import com.ecp.dao.CategoryMapper;
import com.ecp.entity.Category;
import com.ecp.service.front.ICategoryService;
import com.ecp.service.impl.AbstractBaseService;

import tk.mybatis.mapper.entity.Example;

@Service
public class CategoryServiceImpl extends AbstractBaseService<Category, Long> implements ICategoryService {

	CategoryMapper categoryMapper;

	/**
	 * @param mapper
	 *            the mapper to set set方式注入
	 */
	public void setCategoryMapper(CategoryMapper categoryMapper) {
		this.categoryMapper = categoryMapper;
		this.setMapper(mapper);
	}

	/*
	 * (非 Javadoc) Description:此处的算法需要进一步的改进
	 * 
	 * @see com.donglicms.service.front.ICategoryService#getCategoryTree()
	 */
	@Override
	public List<CategoryTreeNode> getCategoryTree() {
		final long FIRST_LEVEL_PARENT_ID = 0; // 一级节点的父ID默认为0

		// 返回的一级目录列表
		List<CategoryTreeNode> categoryList = new ArrayList<CategoryTreeNode>();

		// 查询一级结点 均采用上层数据对象的属性
		Example example = new Example(Category.class);
		
		example.createCriteria().andEqualTo("parentCid", FIRST_LEVEL_PARENT_ID)
								.andEqualTo("deleted", DeletedType.NO);
		example.orderBy("sortNumber");
		List<Category> list = categoryMapper.selectByExample(example);

		for (Category firstLevelNode : list) { // 迭代一级结点

			CategoryTreeNode categoryTreeNode1 = new CategoryTreeNode(); // 生成类目树结点
			ArrayList<CategoryTreeNode> subCategoryList2 = new ArrayList<CategoryTreeNode>(); // 生成当前结点的子结点

			Category category = new Category();
			category.setCid(firstLevelNode.getCid());
			category.setcName(firstLevelNode.getcName());

			categoryTreeNode1.setCategory(category); // 加入当前结点
			categoryTreeNode1.setSubCategoryList(subCategoryList2);

			categoryList.add(categoryTreeNode1);

			// System.out.println("first level cid
			// is:"+firstLevelNode.getCid());

			// 查询二级结点
			Example example2 = new Example(Category.class);
			example2.createCriteria().andEqualTo("parentCid", firstLevelNode.getCid())
										.andEqualTo("deleted", DeletedType.NO);
			example2.orderBy("sortNumber");
			List<Category> list2 = categoryMapper.selectByExample(example2);

			/* System.out.println("second level number:"+list2.size()); */

			for (Category secondLevelNode : list2) { // 迭代二级类目

				CategoryTreeNode categoryTreeNode2 = new CategoryTreeNode(); // 生成类目结点
				ArrayList<CategoryTreeNode> subCategoryList3 = new ArrayList<CategoryTreeNode>(); // 生成当前结点的子结点

				Category category2 = new Category();
				category2.setCid(secondLevelNode.getCid());
				category2.setcName(secondLevelNode.getcName());

				/*
				 * System.out.println("second level node cid:"+secondLevelNode.
				 * getCid());
				 */

				categoryTreeNode2.setCategory(category2); // 加入当前结点
				categoryTreeNode2.setSubCategoryList(subCategoryList3); // 设置当前结点的子结点列表

				subCategoryList2.add(categoryTreeNode2);

				// 查询三级结点 均采用上层业务对象的属性
				Example example3 = new Example(Category.class);
				
				example3.createCriteria().andEqualTo("parentCid", secondLevelNode.getCid())
										 .andEqualTo("deleted", DeletedType.NO);
				example3.orderBy("sortNumber");

				List<Category> list3 = categoryMapper.selectByExample(example3);

				for (Category thirdLevelNode : list3) { // 迭代三级类目

					CategoryTreeNode categoryTreeNode3 = new CategoryTreeNode(); // 生成类目树结点
					categoryTreeNode3.setCategory(thirdLevelNode);
					categoryTreeNode3.setSubCategoryList(null);

					subCategoryList3.add(categoryTreeNode3);
				}

			}

		}

		return categoryList;

	}

	@Override
	public List<Category> getAllCategory() {

		return categoryMapper.selectAll();

	}

	@Override
	public List<CategoryAttrBean> getCategoryAttrListByCid(Long cid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Category> getCategoryByKeywords(String keywords) {
		
		// 读取关键字  分词
		List<Word> words = WordSegmenter.seg(keywords);
		/*
		 * for(Word word:words){ System.out.println("分词结果是："+word.toString()); }
		 */
		List<String> keywordList = new ArrayList<String>();
		for (Word word : words) {
			keywordList.add(word.toString());
		}
		return categoryMapper.getCategoryByKeywords(keywordList);

	}

	@Override
	public Category getCategoryByCid(long cid) {
		Category record =new Category();
		record.setCid(cid);
		record.setDeleted(DeletedType.NO);
		return  categoryMapper.selectOne(record);
		
	}

	@Override
	public List<Category> getCategoryByParentCid(long parentCid) {
		Category record=new Category();
		record.setDeleted(DeletedType.NO);
		record.setParentCid(parentCid);
		return categoryMapper.select(record);
		
	}

}
