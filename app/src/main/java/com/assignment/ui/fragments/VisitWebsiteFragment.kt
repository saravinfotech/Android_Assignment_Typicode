package com.assignment.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.assignment.R
import com.assignment.common.Logger
import com.assignment.databinding.FragmentVisitWebsiteBinding
import com.assignment.viewutils.MessageType
import kotlinx.android.synthetic.main.fragment_visit_website.*

class VisitWebsiteFragment : Fragment() {

    private var binding: FragmentVisitWebsiteBinding? = null
    private var url = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentVisitWebsiteBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.let { layout ->
            getUrlFromBundle()
            setUpWebView(layout)

            layout.webView.webViewClient = object : WebViewClient() {

                override fun shouldOverrideUrlLoading(view: WebView?, urlToLoad: String?): Boolean {
                    Logger.info("shouldOverrideUrlLoading - $urlToLoad")
                    /**
                     * if new url is null then load previous url else load new url
                     */
                    webView.loadUrl(urlToLoad ?: url)
                    return true
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    Logger.info("onPageFinished - $url")
                    layout.progressBar.visibility = View.GONE
                }

                override fun onReceivedError(
                    view: WebView?,
                    request: WebResourceRequest?,
                    error: WebResourceError?
                ) {
                    super.onReceivedError(view, request, error)
                    Logger.info("onReceivedError - $error")
                    if (activity != null && isAdded) {
                        layout.progressBar.visibility = View.GONE
                        MessageType.showInfoMessage(
                            requireActivity(),
                            "${getString(R.string.api_error_message)}\n${error}"
                        )
                    }
                }
            }

            // load url
            Logger.info("webView.loadUrl(url) - $url")
            webView.loadUrl(url)

        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setUpWebView(layout: FragmentVisitWebsiteBinding) {
        val webViewSettings = layout.webView.settings
        webViewSettings.javaScriptEnabled = true
        layout.webView.scrollBarStyle = WebView.SCROLLBARS_OUTSIDE_OVERLAY
    }

    private fun getUrlFromBundle() {
        arguments?.let {
            val args = VisitWebsiteFragmentArgs.fromBundle(it)
            url = args.url
            if (!url.startsWith("http://") && !url.startsWith("https://")) {
                url = "http://$url"
            }
        }
    }
}