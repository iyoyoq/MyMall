package com.server.business.file.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.server.business.file.domain.File;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FileMapper extends BaseMapper<File> {
}
