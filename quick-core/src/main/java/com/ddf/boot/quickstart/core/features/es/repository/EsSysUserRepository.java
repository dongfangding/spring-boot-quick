package com.ddf.boot.quickstart.core.features.es.repository;

import com.ddf.boot.quickstart.core.features.es.mapping.EsSysUser;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * <p>description</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2021/10/13 14:45
 */
@Repository
public interface EsSysUserRepository extends PagingAndSortingRepository<EsSysUser, String> {
}
