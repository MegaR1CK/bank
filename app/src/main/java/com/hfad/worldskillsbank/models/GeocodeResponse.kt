package com.hfad.worldskillsbank.models

import com.google.gson.annotations.SerializedName

data class GeocodeResponse(

	@field:SerializedName("response")
	val response: Response
)

data class AddressDetails(

	@field:SerializedName("Country")
	val country: Country
)

data class Point(

	@field:SerializedName("pos")
	val pos: String
)

data class BoundedBy(

	@field:SerializedName("Envelope")
	val envelope: Envelope
)

data class Country(

	@field:SerializedName("AdministrativeArea")
	val administrativeArea: AdministrativeArea,

	@field:SerializedName("CountryName")
	val countryName: String,

	@field:SerializedName("AddressLine")
	val addressLine: String,

	@field:SerializedName("CountryNameCode")
	val countryNameCode: String
)

data class GeocoderMetaData(

	@field:SerializedName("Address")
	val address: Address,

	@field:SerializedName("AddressDetails")
	val addressDetails: AddressDetails,

	@field:SerializedName("kind")
	val kind: String,

	@field:SerializedName("precision")
	val precision: String,

	@field:SerializedName("text")
	val text: String
)

data class Locality(

	@field:SerializedName("Thoroughfare")
	val thoroughfare: Thoroughfare,

	@field:SerializedName("LocalityName")
	val localityName: String
)

data class AdministrativeArea(

	@field:SerializedName("AdministrativeAreaName")
	val administrativeAreaName: String,

	@field:SerializedName("SubAdministrativeArea")
	val subAdministrativeArea: SubAdministrativeArea
)

data class Response(

	@field:SerializedName("GeoObjectCollection")
	val geoObjectCollection: GeoObjectCollection
)

data class MetaDataProperty(

	@field:SerializedName("GeocoderMetaData")
	val geocoderMetaData: GeocoderMetaData
)

data class Premise(

	@field:SerializedName("PremiseNumber")
	val premiseNumber: String,

	@field:SerializedName("PostalCode")
	val postalCode: PostalCode
)

data class SubAdministrativeArea(

	@field:SerializedName("Locality")
	val locality: Locality,

	@field:SerializedName("SubAdministrativeAreaName")
	val subAdministrativeAreaName: String
)

data class FeatureMemberItem(

	@field:SerializedName("GeoObject")
	val geoObject: GeoObject
)

data class Thoroughfare(

	@field:SerializedName("Premise")
	val premise: Premise,

	@field:SerializedName("ThoroughfareName")
	val thoroughfareName: String
)

data class Address(

	@field:SerializedName("Components")
	val components: List<ComponentsItem>,

	@field:SerializedName("country_code")
	val countryCode: String,

	@field:SerializedName("formatted")
	val formatted: String,

	@field:SerializedName("postal_code")
	val postalCode: String
)

data class Envelope(

	@field:SerializedName("lowerCorner")
	val lowerCorner: String,

	@field:SerializedName("upperCorner")
	val upperCorner: String
)

data class ComponentsItem(

	@field:SerializedName("kind")
	val kind: String,

	@field:SerializedName("name")
	val name: String
)

data class GeoObjectCollection(

	@field:SerializedName("metaDataProperty")
	val metaDataProperty: MetaDataProperty,

	@field:SerializedName("featureMember")
	val featureMember: List<FeatureMemberItem>
)

data class PostalCode(

	@field:SerializedName("PostalCodeNumber")
	val postalCodeNumber: String
)

data class GeoObject(

	@field:SerializedName("metaDataProperty")
	val metaDataProperty: MetaDataProperty,

	@field:SerializedName("boundedBy")
	val boundedBy: BoundedBy,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("Point")
	val point: Point
)

data class GeocoderResponseMetaData(

	@field:SerializedName("request")
	val request: String,

	@field:SerializedName("found")
	val found: String,

	@field:SerializedName("results")
	val results: String
)
