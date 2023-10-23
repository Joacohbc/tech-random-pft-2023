package com.utec.proyectofinaltecnicatura.menufragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.utec.proyectofinaltecnicatura.LoginActivity
import com.utec.proyectofinaltecnicatura.R

class BienvenidaFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        view.findViewById<Button>(R.id.singOutBtn).setOnClickListener {
            requireActivity().getSharedPreferences("AUTHORIZATION", AppCompatActivity.MODE_PRIVATE).edit().remove("token").apply()
            startActivity(Intent(requireContext(), LoginActivity::class.java))
        }
        return view
    }
}