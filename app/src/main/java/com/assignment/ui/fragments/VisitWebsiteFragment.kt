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
import com.assignment.viewutils.Message
import kotlinx.android.synthetic.main.fragment_visit_website.*

class VisitWebsiteFragment : Fragment() {

    // Binding is used to replace the findViewById
    private var binding: FragmentVisitWebsiteBinding? = null

    private var url = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentVisitWebsiteBinding.inflate(inflater, container, false)
        return binding?.root
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.let { layout ->

            /**
             * Take the data from arguments
             */
            arguments?.let {
                val args = VisitWebsiteFragmentArgs.fromBundle(it)
                url = args.url
                if (!url.startsWith("http://") && !url.startsWith("https://")) {
                    url = "http://$url"
                }
            }

            // enable javascript
            val webViewSettings = layout.webView.settings
            webViewSettings.javaScriptEnabled = true

            // set scroll bar
            layout.webView.scrollBarStyle = WebView.SCROLLBARS_OUTSIDE_OVERLAY

            // attach WebViewClient to WebView
            layout.webView.webViewClient = object : WebViewClient() {

                /**
                 * Url is change, should it load changed url or previous url?
                 */
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
                    // loading is finished
                    // hide progress bar
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
                        // error - show error message
                        layout.progressBar.visibility = View.GONE
                        Message.showToast(
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
}