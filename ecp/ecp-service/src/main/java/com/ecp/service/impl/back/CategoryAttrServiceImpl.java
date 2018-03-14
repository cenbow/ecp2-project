package com.ecp.service.impl.back;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.ecp.bean.CategoryAttrBean;
import com.ecp.bean.DeletedType;
import com.ecp.dao.AttributeMapper;
import com.ecp.dao.AttributeValueMapper;
import com.ecp.dao.CategoryAttrMapper;
import com.ecp.dao.CategoryAttrValueMapper;
import com.ecp.entity.Attribute;
import com.ecp.entity.AttributeValue;
import com.ecp.entity.CategoryAttr;
import com.ecp.entity.CategoryAttrValue;
import com.ecp.service.back.IAttributeService;
import com.ecp.service.back.IAttributeValueService;
import com.ecp.service.back.ICategoryAttrService;
import com.ecp.service.back.ICategoryAttrValueService;
import com.ecp.service.impl.AbstractBaseService;

import tk.mybatis.mapper.entity.Example;

@Service("categoryAttrServiceBean")
public class CategoryAttrServiceImpl extends AbstractBaseService<CategoryAttr, Long> implements ICategoryAttrService {

	@Autowired
	CategoryAttrMapper categoryAttrMapper;
	@Autowired
	AttributeMapper attributeMapper;
	@Autowired
	CategoryAttrValueMapper categoryAttrValueMapper;
	@Autowired
	AttributeValueMapper attributeValueMapper;
	
	@Resource(name="attributeServiceBean")
	IAttributeService attributeService;
	@Resource(name="categoryAttrValueServiceBean")
	ICategoryAttrValueService categoryAttrValService;
	@Resource(name="attributeValueServiceBean")
	IAttributeValueService attributeValueService;
	
	

	/**
	 * @param categoryAttrMapper
	 *            the mapper to set set方式注入
	 */

	public void setCategoryAttrMapper(CategoryAttrMapper categoryAttrMapper) {
		this.categoryAttrMapper = categoryAttrMapper;
		setMapper(categoryAttrMapper);
	}

	@Override
	public List<CategoryAttrBean> getCategoryAttrListByCid(Long cid) {

		// categoryAttrMapper.findByCid(cid);
		// List<CategoryAttrBean> list=null;
		List<CategoryAttrBean> list = categoryAttrMapper.getCategoryAttrListByCid(cid);

		return list;
	}

	@Override
	public List<CategoryAttr> findByCid(Long cid) {
		return categoryAttrMapper.findByCid(cid);
	}

	@Override
	public List<Map<String, String>> getCategoryAttrListByCid1(Long cid) {
		return categoryAttrMapper.getCategoryAttrListByCid1(cid);
	}

	final int VALID = 1; // final int DELETED=2;
	
	/**
	 * @see com.ecp.service.back.ICategoryAttrService#saveCategoryAttr(com.ecp.entity.Attribute, com.ecp.entity.CategoryAttr)
	 * 保存类目属性（添加或修改）
	 */
	@Override
	@Transactional
	public int saveCategoryAttr(Attribute attribute, CategoryAttr categoryAttr) {
		
		int rows = 0;
		if(attribute.getAttrId()==null || categoryAttr.getId()==null){
			
			//获取数据库中排序字段的最大值，然后+1设置为当前排序字段；如果数据库中为空则排序字段设置默认值1
			Example example = new Example(CategoryAttr.class);
			example.createCriteria().andEqualTo("deleted", DeletedType.NO).andEqualTo("cid", categoryAttr.getCid()).andEqualTo("attrType", categoryAttr.getAttrType());
			example.setOrderByClause("sort_number DESC");
			List<CategoryAttr> attrList = categoryAttrMapper.selectByExample(example);
			
			if(attrList.isEmpty() || attrList.size()<=0){
				categoryAttr.setSortNumber(1l);
			}else{
				CategoryAttr temp = attrList.get(0);
				categoryAttr.setSortNumber((temp.getSortNumber()+1));
			}
			
			// (1)保存属性，
			attribute.setCreated(new Date());
			attribute.setStatus(VALID);

			rows = attributeService.insertSelective(attribute); // 返回的是记录的个数
			if(rows>0){
				long attr_id = attribute.getAttrId(); // 获取新插入记录的pk

				// (2)保存类目-属性关系
				categoryAttr.setAttrId(attr_id);
				categoryAttr.setCreated(new Date());
				categoryAttr.setStatus((byte) VALID);
				
				rows = categoryAttrMapper.insertSelective(categoryAttr);
				if(rows>0){
					System.out.println("保存类目属性成功");
					return rows;
				}
			}
		}else{
			rows = attributeService.updateByPrimaryKeySelective(attribute);
			if(rows>0){
				rows = categoryAttrMapper.updateByPrimaryKeySelective(categoryAttr);
				if(rows>0){
					System.out.println("修改类目属性值");
					return rows;
				}
			}
		}

		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		return 0;
	}

	@Override
	public List<Map<String, String>> getCategoryAttrValList(Long cid, Long attrId) {
		
		return categoryAttrMapper.getCategoryAttrValList(cid, attrId);
	}

	/**
	 * @see com.ecp.service.back.ICategoryAttrService#saveCategoryAttrValue(com.ecp.entity.AttributeValue, com.ecp.entity.CategoryAttrValue)
	 * 保存类目属性值（添加或修改）
	 */
	@Override
	@Transactional
	public int saveCategoryAttrValue(AttributeValue attrValue, CategoryAttrValue categoryAttrValue) {
		
		int rows = 0;
		if(attrValue.getAttrId()==null || categoryAttrValue.getId()==null){
			
			//获取数据库中排序字段的最大值，然后+1设置为当前排序字段；如果数据库中为空则排序字段设置默认值1
			Example example = new Example(CategoryAttrValue.class);
			example.createCriteria().andEqualTo("deleted", DeletedType.NO).andEqualTo("cid", categoryAttrValue.getCid()).andEqualTo("attrId", categoryAttrValue.getAttrId());
			example.setOrderByClause("sort_number DESC");
			List<CategoryAttrValue> attrValList = categoryAttrValueMapper.selectByExample(example);
			
			if(attrValList.isEmpty() || attrValList.size()<=0){
				categoryAttrValue.setSortNumber(1l);
			}else{
				CategoryAttrValue temp = attrValList.get(0);
				categoryAttrValue.setSortNumber((temp.getSortNumber()+1));
			}
			
			//(1)保存至  属性-值  表中  item_attribute_value
			attrValue.setCreated(new Date());
			attrValue.setStatus(VALID);
			rows = attributeValueMapper.insertSelective(attrValue);
			if(rows>0){
				//（2）保存到 类目-属性-属性值表中  item_attribute_attr_value
				Long valueId=attrValue.getValueId();  //获取属性值 id	
				categoryAttrValue.setValueId(valueId);
				categoryAttrValue.setStatus((byte)VALID);
				categoryAttrValue.setCreated(new Date());
				rows = categoryAttrValueMapper.insertSelective(categoryAttrValue);
				if(rows>0){
					System.out.println("添加类目属性值");
					return rows;
				}
			}
		}else{
			rows = attributeValueMapper.updateByPrimaryKeySelective(attrValue);
			if(rows>0){
				rows = categoryAttrValueMapper.updateByPrimaryKeySelective(categoryAttrValue);
				if(rows>0){
					System.out.println("修改类目属性值");
					return rows;
				}
			}
		}
		
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		return 0;
	}

	/**
	 * @see com.ecp.service.back.ICategoryAttrService#getByAttrId(java.lang.Long)
	 * 根据属性ID查询类目属性表
	 */
	@Override
	public CategoryAttr getByAttrId(Long attrId) {
		Example example = new Example(CategoryAttr.class);
		example.createCriteria().andEqualTo("attrId", attrId);
		List<CategoryAttr> list = categoryAttrMapper.selectByExample(example);
		if(!list.isEmpty() && list.size()==1){
			return list.get(0);
		}
		return null;
	}

	/**
	 * @see com.ecp.service.back.ICategoryAttrService#delCategoryAttr(java.lang.Long)
	 * 根据属性ID删除属性
	 */
	@Override
	@Transactional
	public int delCategoryAttr(Long attrId) {
		Attribute attr = new Attribute();
		attr.setAttrId(attrId);
		attr.setDeleted(DeletedType.YES);
		int rows = attributeService.updateByPrimaryKeySelective(attr);
		if(rows>0){
			Example example = new Example(CategoryAttr.class);
			example.createCriteria().andEqualTo("attrId", attrId);
			CategoryAttr cAttr = new CategoryAttr();
			cAttr.setDeleted(DeletedType.YES);
			rows = categoryAttrMapper.updateByExampleSelective(cAttr, example);
			if(rows>0){
				return rows;
			}
		}
		
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		return 0;
	}

	/**
	 * @see com.ecp.service.back.ICategoryAttrService#delCategoryAttrVal(java.lang.Long)
	 * 根据属性值ID删除属性值
	 */
	@Override
	@Transactional
	public int delCategoryAttrVal(Long valueId) {
		AttributeValue value = new AttributeValue();
		value.setValueId(valueId);
		value.setDeleted(DeletedType.YES);
		int rows = attributeValueService.updateByPrimaryKeySelective(value);
		if(rows>0){
			Example example = new Example(CategoryAttrValue.class);
			example.createCriteria().andEqualTo("valueId", valueId);
			CategoryAttrValue attrVal = new CategoryAttrValue();
			attrVal.setDeleted(DeletedType.YES);
			rows = categoryAttrValService.updateByExampleSelective(attrVal, example);
			if(rows>0){
				return rows;
			}
		}
		
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		return 0;
	}

}
