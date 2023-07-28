package com.gbsoft.util;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Pagination {

    /** 1. 페이지 당 보여지는 게시글의 최대 개수 **/
    private int pageSize = 10;

    /** 3. 현재 페이지 **/
    private int page = 1;

    /** 5. 총 게시글 수 **/
    private int totalListCnt;

    /** 6. 총 페이지 수 **/
    private int totalPageCnt;

    /** 10. DB 접근 시작 index **/
    private int startIndex = 0;

    public Pagination(int totalListCnt, int page) {
        // 총 게시물 수와 현재 페이지를 Controller로 부터 받아온다.

        // 총 게시물 수	- totalListCnt
        // 현재 페이지	- page
        /** 3. 현재 페이지 **/
        setPage(page);

        /** 5. 총 게시글 수 **/
        setTotalListCnt(totalListCnt);

        /** 6. 총 페이지 수 **/
        // 한 페이지의 최대 개수를 총 게시물 수 * 1.0로 나누어주고 올림 해준다.
        // 총 페이지 수 를 구할 수 있다.
        setTotalPageCnt((int) Math.ceil(totalListCnt * 1.0 / pageSize));

        /** 10. DB 접근 시작 index **/
        setStartIndex((page-1) * pageSize);
    }
}