package com.mudhut.software.justiceapp.di

import com.mudhut.software.justiceapp.domain.repositories.auth.AuthenticationRepository
import com.mudhut.software.justiceapp.domain.repositories.posts.PostRepository
import com.mudhut.software.justiceapp.domain.usecases.auth.EmailPasswordLoginUseCase
import com.mudhut.software.justiceapp.domain.usecases.auth.EmailPasswordRegistrationUseCase
import com.mudhut.software.justiceapp.domain.usecases.posts.CreatePostUseCase
import com.mudhut.software.justiceapp.domain.usecases.posts.GetPostsUseCase
import com.mudhut.software.justiceapp.domain.usecases.posts.UpVotePostUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Provides
    fun providesEmailPasswordRegistrationUseCase(repository: AuthenticationRepository) =
        EmailPasswordRegistrationUseCase(repository)

    @Provides
    fun providesEmailPasswordLoginUseCase(repository: AuthenticationRepository) =
        EmailPasswordLoginUseCase(repository)

    @Provides
    fun providesCreatePostUseCase(repository: PostRepository) = CreatePostUseCase(repository)

    @Provides
    fun providesGetPostsUseCase(repository: PostRepository) = GetPostsUseCase(repository)

    @Provides
    fun providesUpVotePostsUseCase(repository: PostRepository) = UpVotePostUseCase(repository)

}
