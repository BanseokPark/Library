package dto;

import java.io.Serializable;

public class BookReviewRepleDto implements Serializable {
	private static final long serialVersionUID = 1L;
	private int reviewindex;
	private String id;
	private String content;
	private String writedate;
	private int group;
	private int step;
	private int depth;
	public BookReviewRepleDto(int reviewindex, String id, String content, String writedate, int group, int step,
			int depth) {
		this.reviewindex = reviewindex;
		this.id = id;
		this.content = content;
		this.writedate = writedate;
		this.group = group;
		this.step = step;
		this.depth = depth;
	}
	public int getReviewindex() {
		return reviewindex;
	}
	public String getId() {
		return id;
	}
	public String getContent() {
		return content;
	}
	public String getWritedate() {
		return writedate;
	}
	public int getGroup() {
		return group;
	}
	public int getStep() {
		return step;
	}
	public int getDepth() {
		return depth;
	}
	public void setReviewindex(int reviewindex) {
		this.reviewindex = reviewindex;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public void setWritedate(String writedate) {
		this.writedate = writedate;
	}
	public void setGroup(int group) {
		this.group = group;
	}
	public void setStep(int step) {
		this.step = step;
	}
	public void setDepth(int depth) {
		this.depth = depth;
	}
	@Override
	public String toString() {
		return "BookReviewRepleDto [reviewindex=" + reviewindex + ", id=" + id + ", content=" + content + ", writedate="
				+ writedate + ", group=" + group + ", step=" + step + ", depth=" + depth + "]";
	}
	
	
}
