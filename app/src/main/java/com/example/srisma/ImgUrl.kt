package com.example.srisma

/**
 * Created by Дом on 12.02.2018.
 */
class ImgUrl(
        var output_url: String,
        var job_id: String)
{
    fun getURL(): String {
        return output_url
    }
    fun getJob(): String {
        return job_id
    }
    override fun toString(): String {
        return "url: %s, job: %s".format(output_url, job_id)
    }
}