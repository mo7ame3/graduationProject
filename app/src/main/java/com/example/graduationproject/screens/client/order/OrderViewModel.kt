package com.example.graduationproject.screens.client.order

import androidx.lifecycle.ViewModel
import com.example.graduationproject.data.WrapperClass
import com.example.graduationproject.model.admin.deleteCraft.Delete
import com.example.graduationproject.model.client.getMyOrder.GetMyOrder
import com.example.graduationproject.model.client.offerOfAnOrder.GetOfferOfAnOrder
import com.example.graduationproject.model.shared.craftList.CraftList
import com.example.graduationproject.model.shared.updateOffer.UpdateOffer
import com.example.graduationproject.repository.ClientRepository
import com.example.graduationproject.repository.SharedRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val sharedRepository: SharedRepository,
    private val clientRepository: ClientRepository
) :
    ViewModel() {
    suspend fun getCraftList(): WrapperClass<CraftList, Boolean, Exception> {
        return sharedRepository.getCraftList()

    }

    suspend fun getMyOrder(
        authorization: String,
        craftId: String,
    ): WrapperClass<GetMyOrder, Boolean, Exception> {
        return clientRepository.getMyOrder(authorization = authorization, craftId = craftId)
    }

    suspend fun deleteOrder(
        authorization: String,
        craftId: String,
        orderId: String
    ): WrapperClass<Delete, Boolean, Exception> {
        return clientRepository.deleteCraft(
            authorization = authorization, craftId = craftId, orderId = orderId
        )
    }

    suspend fun getOfferOfAnOrder(
        authorization: String,
        orderId: String
    ): WrapperClass<GetOfferOfAnOrder, Boolean, Exception> {
        return clientRepository.getOfferOfAnOrder(
            authorization = authorization,
            orderId = orderId
        )
    }

    suspend fun updateOffer(
        authorization: String,
        offerId: String,
        text: String,
        status: String
    ): WrapperClass<UpdateOffer, Boolean, Exception> {
        return sharedRepository.updateOffer(
            authorization = authorization,
            offerId = offerId,
            updateBody = mapOf(
                "text" to text,
                "status" to status
            )
        )
    }
}