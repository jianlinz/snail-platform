package indi.jl.sp.core.domain;

import indi.jl.sp.core.util.CollectionUtil;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.domain.Page;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

/**
 * 分页结果集
 * 替代spring Page
 * 原有spring缺无参构造函数无法进行反序列化
 *
 * @param <T> 泛型
 */
public class PageChunk<T> implements Serializable {

    /**
     * 结果集数据
     */
    @ApiModelProperty("结果集数据")
    private Collection<T> content;

    /**
     * 结果集数据总数
     */
    @ApiModelProperty("结果集数据总数")
    private long totalElements;

    /**
     * 总页数
     */
    @ApiModelProperty("总页数")
    private long totalPages;

    /**
     * 每页条数
     */
    @ApiModelProperty("每页条数")
    private int size;

    /**
     * 当前页数
     */
    @ApiModelProperty("当前页数")
    private int number;

    public PageChunk() {

    }

    public PageChunk(Collection<T> content) {
        this.content = content;
        this.totalElements = null == content ? 0 : content.size();
    }

    public PageChunk(Collection<T> data, long totalElements) {
        this.content = data;
        this.totalElements = totalElements;
        this.size = data == null ? 0 : data.size();
    }

    public PageChunk(Page<T> data) {
        this.content = data.getContent();
        this.totalElements = data.getTotalElements();
        this.size = data.getSize();
        this.number = data.getPageable().getPageNumber();
        this.totalPages = getSize() == 0 ? 1 : (int) Math.ceil((double) this.totalElements / (double) getSize());
    }

    @SuppressWarnings("unchecked")
    public PageChunk(Collection<T> allData, int pageNo, int pageSize) {
        this.size = pageSize;
        if (CollectionUtil.isEmpty(allData)) {
            this.content = Collections.EMPTY_LIST;
            this.totalElements = 0;
        } else {
            Assert.isTrue(pageNo > 0, "Page Number SHOULD FROM 1!");
            int from = (pageNo - 1) * pageSize;
            int to = from + pageSize;
            if (to > allData.size()) {
                to = allData.size();
            }
            if (from > to) {
                this.content = Collections.EMPTY_LIST;
                this.totalElements = allData.size();
            } else {
                this.content = Arrays.asList((T[]) Arrays.copyOfRange(allData.toArray(), from, to));
                this.totalElements = allData.size();
            }
        }
    }

    public Collection<T> getContent() {
        return content;
    }

    public void setContent(Collection<T> content) {
        this.content = content;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public long getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(long totalPages) {
        this.totalPages = totalPages;
    }
}
