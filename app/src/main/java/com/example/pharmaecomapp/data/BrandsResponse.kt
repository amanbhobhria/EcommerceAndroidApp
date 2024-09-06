package com.example.pharmaecomapp.data

import android.os.Parcel
import android.os.Parcelable

data class BrandsResponse(
    val data: List<Brand>
)


data class Brand(
    val id: Int,
    val name: String,
    val image: String, // This will be the URL of the image from the API
    val created_at: String,
    val updated_at: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),  // Read `id`
        parcel.readString() ?: "",  // Read `name`
        parcel.readString() ?: "",  // Read `image` (URL)
        parcel.readString() ?: "",  // Read `created_at`
        parcel.readString() ?: ""   // Read `updated_at`
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(image)
        parcel.writeString(created_at)
        parcel.writeString(updated_at)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Brand> {
        override fun createFromParcel(parcel: Parcel): Brand {
            return Brand(parcel)
        }

        override fun newArray(size: Int): Array<Brand?> {
            return arrayOfNulls(size)
        }
    }
}