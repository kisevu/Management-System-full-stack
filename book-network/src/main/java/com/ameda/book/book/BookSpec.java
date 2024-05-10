package com.ameda.book.book;/*
*
@author ameda
@project Books
*
*/

import org.springframework.data.jpa.domain.Specification;

public class BookSpec {
    public static Specification<Book> withOwnerId(Integer ownerId){
        return (root,query,criteriaBuilder)-> criteriaBuilder.equal(root.get("owner").get("id"),
                ownerId);
    }
}
