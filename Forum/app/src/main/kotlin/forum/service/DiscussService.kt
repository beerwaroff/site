package forum.service

import forum.dto.CommentsDTO
import forum.dto.PostDTO
import forum.dto.PostsDTO

interface DiscussService {

    fun getPosts(): Any

    fun getPost(postId: Int): Any

    fun sendComment(postId: Int, dto: CommentsDTO): Any

    fun createPost(dto: PostDTO): Any

    fun deletePost(postId: Int): Any
}