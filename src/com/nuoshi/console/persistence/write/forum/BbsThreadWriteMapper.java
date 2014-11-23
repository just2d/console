package com.nuoshi.console.persistence.write.forum;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

public interface BbsThreadWriteMapper {

//	/**
//	 * 更新帖子状态
//	 * @param authorId
//	 * @param status
//	 * @return
//	 */
//	@Update("update tf_bbs_threads set status = #{toStatus} where author_id = #{authorId} and status = #{origStatus}")
//	public int updateStatusByAuthorId(@Param("authorId")int authorId,@Param("toStatus")int toStatus,@Param("origStatus")int origStatus);
//	
//	
	
	/**
	 * 更新threads状态
	 * @param authorId
	 * @param status
	 * @return
	 */
	@Update("update tf_bbs_threads set status = #{toStatus} where author_id = #{authorId} and status = #{origStatus}")
	public int updateThreadsStatusByAuthorId(@Param("authorId")int authorId,@Param("toStatus")int toStatus,@Param("origStatus")int origStatus);
	
	/**
	 * 更新posts状态
	 * @param authorId
	 * @param status
	 * @return
	 */
	@Update("update tf_bbs_posts set status = #{toStatus} where author_id = #{authorId} and status = #{origStatus}")
	public int updatePostsStatusByAuthorId(@Param("authorId")int authorId,@Param("toStatus")int toStatus,@Param("origStatus")int origStatus);
	
}
