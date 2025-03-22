package com.topics.discuss.model.bean;

import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "article")
@Getter
@Setter
@AllArgsConstructor
public class ArticleBean {

	@Id
	@Column(name = "article_id")
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

	public ArticleBean() {
	}

	@Override
	public String toString() {
		return "ArticleBean [articleId=" + articleId + ", memberId=" + memberId + ", title=" + title + ", content="
				+ content + ", categoryId=" + categoryId + ", viewCount=" + viewCount + ", isPinned=" + isPinned
				+ ", isFeatured=" + isFeatured + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt
				+ ", isDeleted=" + isDeleted + "]";
	}
}
