package com.anangkur.mediku.feature.about

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.anangkur.mediku.R
import com.anangkur.mediku.data.Repository
import com.anangkur.mediku.data.model.about.Resource

class AboutViewModel(private val repository: Repository): ViewModel() {

    val listResourceLive = MutableLiveData<List<Resource>>()
    fun createResourceData(){
        val listResource = ArrayList<Resource>()
        listResource.add(createResourceFreepik())
        listResource.add(createResourcePixelPerfect())
        listResource.add(createResourceKirashastry())
        listResource.add(createResourceGoodWare())
        listResource.add(createResourceNhorPhai())
        listResource.add(createResourceEducalyp())
        listResourceLive.postValue(listResource)
    }

    private fun createResourceFreepik(): Resource {
        val listResourceChild = ArrayList<Resource.ResourceChild>()
        listResourceChild.add(
            Resource.ResourceChild(
                image = R.drawable.ic_doctor,
                link = "https://www.flaticon.com/free-icon/doctor_2679283",
                name = "Doctor"
            ))
        listResourceChild.add(
            Resource.ResourceChild(
                image = R.drawable.ic_baby_event,
                link = "https://www.flaticon.com/free-icon/baby-boy_2641657",
                name = "Baby Boy"
            )
        )
        listResourceChild.add(
            Resource.ResourceChild(
                image = R.drawable.ic_calendar,
                name = "Calendar",
                link = "https://www.flaticon.com/free-icon/calendar_833595"
            )
        )
        listResourceChild.add(
            Resource.ResourceChild(
                image = R.drawable.ic_google,
                link = "https://www.flaticon.com/free-icon/google_2702602",
                name = "Google"
            )
        )
        listResourceChild.add(
            Resource.ResourceChild(
                image = R.drawable.ic_mediku_512,
                name = "Prescription",
                link = "https://www.flaticon.com/free-icon/prescription_1047670"
            )
        )
        listResourceChild.add(
            Resource.ResourceChild(
                image = R.drawable.ic_profile_active,
                name = "Student",
                link = "https://www.flaticon.com/free-icon/student_167750"
            )
        )
        return Resource(
            title = "Icon made by Freepik from www.flaticon.com",
            child = listResourceChild,
            link = "https://www.flaticon.com/authors/freepik"
        )
    }

    private fun createResourcePixelPerfect(): Resource {
        val listResourceChild = ArrayList<Resource.ResourceChild>()
        listResourceChild.add(
            Resource.ResourceChild(
                image = R.drawable.ic_blood_event,
                name = "Blood Drop",
                link = "https://www.flaticon.com/free-icon/blood-drop_893529"
            )
        )
        listResourceChild.add(
            Resource.ResourceChild(
                image = R.drawable.ic_dashboard_active,
                name = "Dashboard",
                link = "https://www.flaticon.com/free-icon/dashboard_1828673"
            )
        )
        return Resource(
            title = "Icon made by Pixel perfect from www.flaticon.com",
            link = "https://www.flaticon.com/authors/pixel-perfect",
            child = listResourceChild
        )
    }

    private fun createResourceKirashastry(): Resource {
        val listResourceChild = ArrayList<Resource.ResourceChild>()
        listResourceChild.add(
            Resource.ResourceChild(
                image = R.drawable.ic_edit,
                link = "https://www.flaticon.com/free-icon/edit_1159633",
                name = "Edit"
            )
        )
        return Resource(
            title = "Icon made by Pixel perfect from www.flaticon.com",
            link = "https://www.flaticon.com/authors/kiranshastry",
            child = listResourceChild
        )
    }

    private fun createResourceGoodWare(): Resource {
        val listResourceChild = ArrayList<Resource.ResourceChild>()
        listResourceChild.add(
            Resource.ResourceChild(
                image = R.drawable.ic_first_aid_kit_black,
                link = "https://www.flaticon.com/free-icon/first-aid-kit_862032",
                name = "First Aid Kit"
            )
        )
        return Resource(
            title = "Icon made by Good Ware from www.flaticon.com",
            link = "https://www.flaticon.com/authors/good-ware",
            child = listResourceChild
        )
    }

    private fun createResourceNhorPhai(): Resource {
        val listResourceChild = ArrayList<Resource.ResourceChild>()
        listResourceChild.add(
            Resource.ResourceChild(
                image = R.drawable.ic_healthy_black,
                link = "https://www.flaticon.com/free-icon/healthy_1813334",
                name = "Healty"
            )
        )
        return Resource(
            title = "Icon made by Nhor Phai from www.flaticon.com",
            link = "https://www.flaticon.com/authors/nhor-phai",
            child = listResourceChild
        )
    }

    private fun createResourceEducalyp(): Resource {
        val listResourceChild = ArrayList<Resource.ResourceChild>()
        listResourceChild.add(
            Resource.ResourceChild(
                image = R.drawable.ic_pills_black,
                link = "https://www.flaticon.com/free-icon/drug_2650500",
                name = "Drug"
            )
        )
        return Resource(
            title = "Icon made by Educalyp from www.flaticon.com",
            link = "https://www.flaticon.com/authors/eucalyp",
            child = listResourceChild
        )
    }
}