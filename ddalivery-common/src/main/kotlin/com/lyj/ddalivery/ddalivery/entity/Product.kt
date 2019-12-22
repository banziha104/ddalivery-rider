package com.lyj.ddalivery.ddalivery.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id


/***
 * 상품
 */
@Entity
data class Product(
        @GeneratedValue @Id var productId : Long,
        @Column var productName : String
)