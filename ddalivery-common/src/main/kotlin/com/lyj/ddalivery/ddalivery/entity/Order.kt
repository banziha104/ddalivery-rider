package com.lyj.ddalivery.ddalivery.entity

import com.lyj.ddalivery.ddalivery.base.AuditModel
import javax.persistence.*


@Entity
data class Order(
        @GeneratedValue(strategy = GenerationType.IDENTITY) @Id val orderId : Long,
        @Column var clientId : Long, // 주문자명
        @Column var productId : Long // 아이템명
) : AuditModel<Long>() {
    override fun getId(): Long? {
        return orderId
    }
}