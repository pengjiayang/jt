package com.jt.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jt.pojo.ItemCat;

public interface ItemCatMapper extends BaseMapper<ItemCat> {
	List<ItemCat> findItemCatTree(@Param("parentId")Long[] parentId);
}
