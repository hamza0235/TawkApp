package com.example.tawkapp.model.users

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity(tableName = "users")
class Users(


    var login: String? = null,

    @PrimaryKey
    var id: Int? = null,

    var node_id: String? = null,


    var avatar_url: String? = null,


    var gravatar_id: String? = null,


    var url: String? = null,


    var html_url: String? = null,


    var followers_url: String? = null,


    var following_url: String? = null,

    var gists_url: String? = null,

    var starred_url: String? = null,

    var subscriptions_url: String? = null,


    var organizations_url: String? = null,

    var repos_url: String? = null,

    var events_url: String? = null,


    var received_events_url: String? = null,

    var type: String? = null,

    var notes: String? = null,

    var site_admin: Boolean? = null

) : Serializable