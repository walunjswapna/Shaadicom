package com.shadi.local_db.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey


data class CandidateListResponse(
	val results: List<ResultsItem?>? =null,
	val info: Info? = null
)
	data class Dob(
		val date: String?="" ,
		val age: Int?=0
	)
	data class Id(
		val name: String?="" ,
		val value: String?=""
	)
	data class Info(
		val seed: String? ,
		val results: Int? = 0,
		val page: Int? = 0,
		val version: String? 
	)

	data class Location(
		val city: String?="",
		val state: String?="",
		val country: String? ="",
		val postcode: Int? =0
	)
	data class Login(
		val uuid: String?="" ,
		val username: String?="" ,
		val password: String?="",
		val salt: String?="" ,
		val md5: String?="",
		val sha1: String?="",
		val sha256: String? =""
	)
	data class Name(
		val title: String?="" ,
		val first: String?="" ,
		val last: String? =""
	)
	data class Picture(
		val large: String? ,
		val medium: String? ,
		val thumbnail: String? 
	)


	data class Registered(
		val rdate: String?="" ,
		val rage: Int? = 0
	)

@Entity(tableName = "candidate")
data class ResultsItem(
	@PrimaryKey(autoGenerate = true)
	val ID:Int=0,
	var isInterested:Int=0,
	@Embedded
	val id: Id?=Id(),
	val gender: String?,
	@Embedded
	val name: Name?=Name(),
	@Embedded
	val location: Location?= Location(),
	val email: String?="",
	@Embedded
	val login: Login?=Login(),
	@Embedded
	val dob: Dob?,
	@Embedded
	val registered: Registered?= Registered(),
	val phone: String?="",
	val cell: String?="",
	@Embedded
	val picture: Picture?,
	val nat: String?
)
data class Street(
	val number: Int? = 0,
	val name: String? =""
)
