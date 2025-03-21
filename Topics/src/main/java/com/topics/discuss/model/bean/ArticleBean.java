package com.topics.discuss.model.bean;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity @Table(name = "article")
public class ArticleBean {
	
	@Id @Column(name = "article_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int articleId;
	
	@Column(name = "member_Id")
    private int memberId;
	
	@Column(name = "title")
    private String title;
	
	@Column(name = "content")
    private String content;
	
	@Column(name = "category_id")
    private int categoryId;
	
	@Column(name = "view_count")
    private int viewCount;
	
	@Column(name = "is_pinned")
    private boolean isPinned;
	
	@Column(name = "is_featured")
    private boolean isFeatured;
	
	@Column(name = "created_at")
    private LocalDateTime createdAt;
	
	@Column(name = "updated_at")
    private LocalDateTime updatedAt;
	
	@Column(name = "is_deleted")
    private boolean isDeleted;

    public ArticleBean() {}

    public ArticleBean(int articleId, int memberId, String title, String content, int categoryId, int viewCount,
                   boolean isPinned, boolean isFeatured, LocalDateTime createdAt, LocalDateTime updatedAt, boolean isDeleted) {
        this.articleId = articleId;
        this.memberId = memberId;
        this.title = title;
        this.content = content;
        this.categoryId = categoryId;
        this.viewCount = viewCount;
        this.isPinned = isPinned;
        this.isFeatured = isFeatured;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.isDeleted = isDeleted;
    }

    // Getters and Setters
    public int getArticleId() { return articleId; }
    public void setArticleId(int articleId) { this.articleId = articleId; }

    public int getMemberId() { return memberId; }
    public void setMemberId(int memberId) { this.memberId = memberId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public int getCategoryId() { return categoryId; }
    public void setCategoryId(int categoryId) { this.categoryId = categoryId; }

    public int getViewCount() { return viewCount; }
    public void setViewCount(int viewCount) { this.viewCount = viewCount; }

    public boolean isPinned() { return isPinned; }
    public void setPinned(boolean pinned) { isPinned = pinned; }

    public boolean isFeatured() { return isFeatured; }
    public void setFeatured(boolean featured) { isFeatured = featured; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public boolean isDeleted() { return isDeleted; }
    public void setDeleted(boolean deleted) { isDeleted = deleted; }

	@Override
	public String toString() {
		return "ArticleBean [articleId=" + articleId + ", memberId=" + memberId + ", title=" + title + ", content="
				+ content + ", categoryId=" + categoryId + ", viewCount=" + viewCount + ", isPinned=" + isPinned
				+ ", isFeatured=" + isFeatured + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt
				+ ", isDeleted=" + isDeleted + "]";
	}
}
