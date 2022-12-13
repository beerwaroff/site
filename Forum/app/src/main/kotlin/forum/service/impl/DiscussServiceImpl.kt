package forum.service.impl

import forum.dto.CommentsDTO
import forum.dto.PostDTO
import forum.dto.PostsDTO
import forum.model.Comments
import forum.model.Followers
import forum.model.Posts
import forum.service.DiscussService
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime
import org.springframework.stereotype.Service

@Service
class DiscussServiceImpl : DiscussService {

    class Post(_post: List<PostDTO>, _comments: List<CommentsDTO>, _postId: Int) {
        val post = _post
        val comments = _comments
        val postId = _postId
    }
    override fun getPosts(): Any {
        val posts = transaction {
            Posts.slice(Posts.id, Posts.userId, Posts.username, Posts.name).selectAll().map {
                Posts.toPostsDto(it)
            }
        }

        return posts
    }

    override fun getPost(postId: Int): Any {
        val post = transaction {
            Posts.select { Posts.id eq postId }.map {
                Posts.toPostDto(it)
            }
        }

        val comments = transaction {
            Comments.select { Comments.postId eq postId}.map {
                Comments.toCommentsDto(it)
            }
        }

        return Post(post, comments, postId)
    }
    private fun Posts.toPostsDto(row: ResultRow) =
        PostsDTO (
            id = row[id],
            name = row[name],
            username = row[username],
        )

    override fun sendComment(postId: Int, dto: CommentsDTO): Any {
        transaction {
            Comments.insert {
                it[Comments.postId] = dto.postId
                it[Comments.userId] = dto.userId
                it[Comments.username] = dto.username
                it[Comments.text] = dto.text
            }
        }
        return getPost(postId)
    }

    override fun createPost(dto: PostDTO): Any {
        transaction {
            Posts.insert {
                it[Posts.userId] = dto.userId
                it[Posts.name] = dto.name
                it[Posts.username] = dto.username
                it[Posts.text] = dto.text
                it[Posts.created] = DateTime()
            }
        }

        return getPosts()
    }

    override fun deletePost(postId: Int): Any {
        transaction { Comments.deleteWhere { Comments.postId eq postId} }
        transaction { Posts.deleteWhere { Posts.id eq postId} }
        return try {
            "Пост удален"
        } catch (e: Exception) {
            "Что-то пошло не так, обратитесь к главному администратору."
        }
    }
    private fun Comments.toCommentsDto(row: ResultRow) =
        CommentsDTO (
            id = row[id],
            userId = row[userId],
            username = row[username],
            postId = row[postId],
            text = row[text]
        )

    private fun Posts.toPostDto(row: ResultRow) =
        PostDTO (
            id = row[Posts.id],
            name = row[Posts.name],
            userId = row[Posts.userId],
            username = row[Posts.username],
            text = row[Posts.text]
        )
}