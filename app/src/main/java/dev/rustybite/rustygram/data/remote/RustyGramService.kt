package dev.rustybite.rustygram.data.remote

import com.google.gson.JsonObject
import dev.rustybite.rustygram.data.dtos.auth.UserDto
import dev.rustybite.rustygram.data.dtos.auth.VerifiedUserDto
import dev.rustybite.rustygram.data.dtos.bookmark.BookmarkDto
import dev.rustybite.rustygram.data.dtos.comments.CommentDto
import dev.rustybite.rustygram.data.dtos.like.LikeDto
import dev.rustybite.rustygram.data.dtos.posts.PostDto
import dev.rustybite.rustygram.data.dtos.profile.ProfileDto
import dev.rustybite.rustygram.domain.models.RustyResponse
import dev.rustybite.rustygram.util.API_KEY
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface RustyGramService {

    @POST("/auth/v1/signup")
    @Headers("apiKey: $API_KEY", "Content-Type: application/json")
    suspend fun registerUser(
        @Body body: JsonObject
    ): Response<VerifiedUserDto>

    @POST("/auth/v1/logout")
    @Headers("apiKey: $API_KEY", "Content-Type: application/json")
    suspend fun logout(
        @Header("Authorization") token: String
    ): Response<RustyResponse>

    @POST("/auth/v1/token?grant_type=password")
    @Headers("apiKey: $API_KEY", "Content-Type: application/json")
    suspend fun login(
        @Body body: JsonObject
    ): Response<VerifiedUserDto>

    @POST("/auth/v1/token?grant_type=refresh_token")
    @Headers("apiKey: $API_KEY", "Content-Type: application/json")
    suspend fun refreshToken(
        @Body body: JsonObject
    ): Response<VerifiedUserDto>

    @POST("rest/v1/profiles")
    @Headers("apiKey: $API_KEY", "Content-Type: application/json")
    suspend fun createProfile(
        @Header("Authorization") token: String,
        @Body body: JsonObject
    ): Response<Unit>

    @GET("rest/v1/profiles")
    @Headers("apiKey: $API_KEY", "Content-Type: application/json")
    suspend fun getProfile(
        @Header("Authorization") token: String,
        @Query("user_id") userId: String
    ): Response<List<ProfileDto>>

    @GET("rest/v1/profiles")
    @Headers("apiKey: $API_KEY", "Content-Type: application/json")
    suspend fun getProfiles(
        @Header("Authorization") token: String
    ): Response<List<ProfileDto>>

    @GET("auth/v1/user")
    @Headers("apiKey: $API_KEY", "Content-Type: application/json")
    suspend fun getLoggedInUser(
        @Header("Authorization") token: String
    ): Response<UserDto>

    @POST("rest/v1/posts")
    @Headers("apiKey: $API_KEY", "Content-Type: application/json")
    suspend fun createPost(
        @Header("Authorization") token: String,
        @Body body: JsonObject
    ): Response<Unit>

    @GET("rest/v1/posts")
    @Headers("apiKey: $API_KEY", "Content-Type: application/json")
    suspend fun getFeeds(
        @Header("Authorization") token: String,
    ): Response<List<PostDto>>

    @POST("/rest/v1/bookmarks")
    @Headers("apiKey: $API_KEY", "Content-Type: application/json")
    suspend fun bookmarkPost(
        @Header("Authorization") token: String,
        @Body body: JsonObject
    ): Response<Unit>

    @DELETE("/rest/v1/bookmarks")
    @Headers("apiKey: $API_KEY", "Content-Type: application/json")
    suspend fun unBookmarkPost(
        @Header("Authorization") token: String,
        @Query("bookmark_id") bookmarkId: String
    ): Response<Unit>

    @GET("/rest/v1/bookmarks")
    @Headers("apiKey: $API_KEY", "Content-Type: application/json")
    suspend fun getBookmarks(
        @Header("Authorization") token: String
    ): Response<List<BookmarkDto>>

    @POST("/rest/v1/likes")
    @Headers("apiKey: $API_KEY", "Content-Type: application/json")
    suspend fun likePost(
        @Header("Authorization") token: String,
        @Body body: JsonObject
    ): Response<Unit>

    @DELETE("/rest/v1/likes")
    @Headers("apiKey: $API_KEY", "Content-Type: application/json")
    suspend fun unlikePost(
        @Header("Authorization") token: String,
        @Query("like_id") likeId: String
    ): Response<Unit>

    @GET("/rest/v1/likes")
    @Headers("apiKey: $API_KEY", "Content-Type: application/json")
    suspend fun getLikes(
        @Header("Authorization") token: String
    ): Response<List<LikeDto>>

    @POST("/rest/v1/comments")
    @Headers("apiKey: $API_KEY", "Content-Type: application/json")
    suspend fun createComment(
        @Header("Authorization") token: String,
        @Body body: JsonObject
    ): Response<Unit>

    @DELETE("/rest/v1/comments")
    @Headers("apiKey: $API_KEY", "Content-Type: application/json")
    suspend fun deleteComment(
        @Header("Authorization") token: String,
        @Query("comment_id") commentId: String
    ): Response<Unit>

    @GET("/rest/v1/comments")
    @Headers("apiKey: $API_KEY", "Content-Type: application/json")
    suspend fun getPostComments(
        @Header("Authorization") token: String,
        @Query("post_id") postId: String
    ): Response<List<CommentDto>>

    @GET("/rest/v1/comments")
    @Headers("apiKey: $API_KEY", "Content-Type: application/json")
    suspend fun getComments(
        @Header("Authorization") token: String
    ): Response<List<CommentDto>>
}