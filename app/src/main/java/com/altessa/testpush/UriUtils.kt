package com.altessa.testpush

import android.content.ContentResolver
import android.content.Context
import android.content.res.Resources
import android.net.Uri
import androidx.annotation.AnyRes


object UriUtils {
    @Throws(Resources.NotFoundException::class)
    fun getUriToResource(context: Context, @AnyRes resId: Int): Uri {
        val res = context.resources

        val resUri = Uri.parse(
            ContentResolver.SCHEME_ANDROID_RESOURCE +
                    "://" + res.getResourcePackageName(resId)
                    + '/' + res.getResourceTypeName(resId)
                    + '/' + res.getResourceEntryName(resId)
        )
        return resUri
    }
}