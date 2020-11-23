package com.iguigui.qqbot.entity


/**
 * <p>
 * 
 * </p>
 *
 * @author iguigui
 * @since 2020-11-21
 */
class Messages : BaseEntity() {


    /**
     * 消息类型，0私聊1群聊
     */
    var messageType: Int? = null

    /**
     * 发送方
     */
    var senderId: Long? = null

    /**
     * 接收方
     */
    var receiverId: Long? = null

    var groupId: Long? = null

    /**
     * 发送方昵称
     */
    var senderName: String? = null

    /**
     * 接收方昵称
     */
    var receiverName: String? = null

    var groupName: String? = null

    /**
     * 消息详情
     */
    var messageDetail: String? = null


    override fun toString(): String {
        return "Messages{" +
        "id=" + id +
        ", createTime=" + createTime +
        ", creator=" + creator +
        ", modifyTime=" + modifyTime +
        ", modifier=" + modifier +
        ", messageType=" + messageType +
        ", senderId=" + senderId +
        ", receiverId=" + receiverId +
        ", groupId=" + groupId +
        ", senderName=" + senderName +
        ", receiverName=" + receiverName +
        ", groupName=" + groupName +
        ", messageDetail=" + messageDetail +
        "}"
    }
}
