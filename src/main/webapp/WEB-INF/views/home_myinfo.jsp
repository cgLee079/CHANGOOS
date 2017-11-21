<style>
.ml6 {
	color: #000;
	position: relative;
	font-weight: 900;
	font-size: 3.3em;
}

.ml6 .text-wrapper {
	position: relative;
	display: inline-block;
	padding-top: 0.2em;
	padding-right: 0.05em;
	padding-bottom: 0.1em;
	overflow: hidden;
}

.ml6 .letter {
	display: inline-block;
	line-height: 1em;
}
</style>

<style>
.wrap-myinfo {
	background: rgba(255, 255, 255, 1);
	position : fixed;
	top : 0;
	left : 0;
	bottom : 0;
	right : 0;
}

.slider {
	overflow-y: hidden;
	bottom: 0; /* approximate max height */
	transition-property: all;
	transition-duration: .5s;
	transition-timing-function: cubic-bezier(0, 1, 0.5, 1);
}

.slider.closed {
	bottom: 100%;
}

.myinfo-views{
	position : relative;
	margin-top: 5%;
	width: 100%;
	height: 500px;
}

.content-picture{
	flex : 1 50%;
	background-position	: center;
    background-repeat	: no-repeat;
    background-size		: cover;
}

.content-text{
	flex : 1 40%;
	padding : 30px;
}

.myinfo-view {
	display : flex;
	flex-flow: row wrap;
	position: absolute;
	width : 100%;
	height: 100%;
}

.myinfo-view00 {left: 0%; background: #FFF;}
.myinfo-view01 {left: 100%; background: #333;}
.myinfo-view02 {left: 100%; background: #666;}
.myinfo-view03 {left: 100%; background: #999;}

.btns-view{
	width : 100%;
	text-align: center;
	margin-top : 30px;
}

.btn-view{
	display : inline-block;
	width : 20px;
	height: 20px;
	border-radius : 10px;
	background : #888;
	margin : 20px 10px 0px 10px;
	cursor : pointer;
}

.btn-view.on{
	background : #000;
}

</style>
<script type="text/javascript">

</script>
</head>
<body>
</body>
</html>
