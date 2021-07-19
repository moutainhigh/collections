<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<nav id="common-nav" class="navbar navbar-inverse navbar-fixed-top">
	<div class="container">
		<!-- Brand and toggle get grouped for better mobile display -->
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
				<span class="sr-only">Toggle navigation</span> <span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="/"> <img class="navbar-logo" src="statics/images/logo.png" width="50" style="position: absolute;top:0">
			</a>
		</div>

		<!-- Collect the nav links, forms, and other content for toggling -->
		<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
			<ul class="nav navbar-nav navbar-right">
				<li><a href="https://github.com/bazelbuild/bazel"> <span class="hidden-sm">GitHub</span> <span class="nav-icon visible-sm"><i class="fa fa-github"></i></span>
				</a></li>
			</ul>
			<form class="navbar-form navbar-right" action="/search.html" id="cse-search-box">
				<div class="form-group">
					<input type="hidden" name="cx" value="012346921571893344015:xv_nfgpzbu4"> <input type="hidden" name="cof" value="FORID:10"> <input type="hidden" name="ie" value="UTF-8"> <input type="search" name="q" class="form-control input-sm" placeholder="Search">
				</div>
			</form>
			<ul class="nav navbar-nav navbar-right">
				<li><a href="https://docs.bazel.build">Documentation</a></li>
				<li><a href="/contributing.html">Contribute</a></li>
				<li><a href="https://blog.bazel.build"> <span class="hidden-sm">Blog</span> <span class="nav-icon visible-sm"><i class="fa fa-rss"></i></span>
				</a></li>
				<li><a href="https://twitter.com/bazelbuild"> <span class="visible-xs">Twitter</span> <span><i class="nav-icon fa fa-twitter hidden-xs"></i></span>
				</a></li>
				<li><a href="http://stackoverflow.com/questions/tagged/bazel"> <span class="visible-xs">StackOverflow</span> <span><i class="nav-icon fa fa-stack-overflow hidden-xs"></i></span>
				</a></li>
			</ul>
		</div>
		<!-- /.navbar-collapse -->
	</div>
	<!-- /.container-fluid -->
</nav>