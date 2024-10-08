package com.example.android.camera2.video.kt_fragments

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.camera.utils.GenericListAdapter
import com.example.android.camera2.video.R

/**
 * In this [Fragment] we let users pick a transfer curve for the preview output when using the
 * hardware pipeline with HDR.
 */
class TransferFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = RecyclerView(requireContext())

    @SuppressLint("MissingPermission")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("FragmentName", javaClass.simpleName)
        view as RecyclerView
        view.apply {
            layoutManager = LinearLayoutManager(requireContext())
            val transferList = enumerateTransferCharacteristics()

            val layoutId = android.R.layout.simple_list_item_1
            adapter = GenericListAdapter(transferList, itemLayoutId = layoutId) { view, item, _ ->
                view.findViewById<TextView>(android.R.id.text1).text =
                    "Preview Transfer " + item.name
                view.setOnClickListener {
                    val navController =
                        Navigation.findNavController(requireActivity(), R.id.fragment_container)

                }
            }
        }
    }

    companion object {

        private data class TransferInfo(
            val name: String,
            val id: Int
        )

        public val PQ_STR = "PQ"
        public val LINEAR_STR = "LINEAR"
        public val HLG_STR = "HLG (Android 14 or above)"
        public val HLG_WORKAROUND_STR = "HLG (Android 13)"
        public val PQ_ID: Int = 0
        public val LINEAR_ID: Int = 1
        public val HLG_ID: Int = 2
        public val HLG_WORKAROUND_ID: Int = 3

        public fun idToStr(transferId: Int): String = when (transferId) {
            PQ_ID -> PQ_STR
            LINEAR_ID -> LINEAR_STR
            HLG_ID -> HLG_STR
            HLG_WORKAROUND_ID -> HLG_WORKAROUND_STR
            else -> throw RuntimeException("Unexpected transferId " + transferId)
        }

        /** Lists all video-capable cameras and supported resolution and FPS combinations */
        @SuppressLint("InlinedApi")
        private fun enumerateTransferCharacteristics(): List<TransferInfo> {
            val transferCharacteristics: MutableList<TransferInfo> = mutableListOf()
            transferCharacteristics.add(TransferInfo(PQ_STR, PQ_ID))
            transferCharacteristics.add(TransferInfo(LINEAR_STR, LINEAR_ID))
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.TIRAMISU) {
                transferCharacteristics.add(TransferInfo(HLG_STR, HLG_ID))
            } else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.TIRAMISU) {
                transferCharacteristics.add(TransferInfo(HLG_WORKAROUND_STR, HLG_WORKAROUND_ID))
            }
            return transferCharacteristics
        }
    }
}
