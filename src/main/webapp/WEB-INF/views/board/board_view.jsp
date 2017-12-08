<%@ page pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/WEB-INF/views/included/included_head.jsp" %> 
<script>
</script>
</head>
<body>
	<div class="wrapper">
		<c:import url="../included/included_nav.jsp" charEncoding="UTF-8" />
		
		<style>
			.wrap-board{
				width : 80%; 
				margin: 0px auto;
				margin-top: 3rem;
			}
			
			.board-submenu{
				text-align: right;
				font-size: 0.6rem;
				margin-bottom: 0.2rem;
			}
			
			.board-submenu .btn{
				padding-left: 1rem;
			}
			
			.board-detail{
				border: 1px solid #DDD;
				background: #FFF;
			}
			
			.board-detail .board-head{
				padding : 1rem;
			}
			
			.board-detail .board-title{
				font-size: 2rem;
				font-weight: bold;
			}
			
			.board-detail .board-info{
				text-align: right;
				font-size: 0.6rem;
				padding : 0.2rem;
			}
			
			.board-detail .board-info a{
				padding : 0 1rem;
			}
			
			.board-detail .board-contents{
				color : #444;
				font-size : 0.8rem;
				margin: 2rem 1rem 1rem 1rem;
			}
			
			.board-contents h1, h2, h3{
				margin: 0.5rem 0px;
			}
			
			.board-contents p{
				margin : 0.3rem 0;
				line-height: 1.3rem;
			}
			
			.board-contents img{
				height : auto;
			}
			
			.board-contents code {
				overflow-x: auto;
				margin: 1rem 0.1rem;
				border: 1px solid #DDD;
			}
		</style>
		
		<div class="wrap-board">
			<div class="board-submenu">
				<a class="btn">목록</a>
				<a class="btn">이전글</a>
				<a class="btn">다음글</a>
			</div>
			<div class="board-detail">
				<div class="board-head">
					<div class="board-title">${board.title}</div>
					<div style="height : 1px; background: #CCC; margin: 0.5rem 0rem" ></div>
					<div class="board-info">
						<a>${board.sect}</a>
						<a>${board.date}</a>
						<a>조회수 ${board.hits}</a>
					</div>
				</div>
				
				<div class="board-contents">	
						<h2>OCP, Open-Closed Principle (개방-폐쇄 원칙)</h2>
					
						<p>소프트웨어 개체(클래스, 모듈, 함수 등등)는 확장에 대해 열려 있어야 하고,<br/>
						수정에 대해서는 닫혀 있어야 한다<br/>
						<br/>
						기존의 코드를 변경하지 않으면서, 기능을 추가 할 수 있도록 설계한다.</p>
						
						<p><br/>
						&nbsp;</p>
						
						<h3>#Before Branch</h3>
						
						<p><img alt="" src="/resources/image/item/contents/content_171205_132752_OCP before.jpg" style="height:300px; width:470px"/><br/>
						<br/>
						AreaCalculator 클래스는 shape들의 넓이의 합을 계산하고.<br/>
						ConsolePrinter 클래스는 결과를 출력한다.<br/>
						<br/>
						이 프로그램은 OCP원칙이 위배됨을 보여준다</p>
						
						<p>&nbsp;</p>
						
						<p>&nbsp;</p>
						
						<p><strong>AreaCaclualotr.java</strong></p>
						
						<pre>
						<code>package package com.cglee079.ocp;
						
						import java.util.ArrayList;
						import java.util.Iterator;
						
						public class AreaCalculator {
								private ArrayList shapes = new ArrayList();
								private double areasum;
								
								public double getAreasum() {
									return areasum;
								}
						
								public void calcuate(){
										Iterator iter= shapes.iterator();
										areasum = 0.0;
										while(iter.hasNext()){
											Shape curShape = iter.next();
											areasum += curShape.area();
										}
								}
										
								public void addShape(Shape s){
									shapes.add(s);
								}
						}
						</code></pre>
						
						<p>위의 클래스는 넓이의 합을 계산한다.</p>
						
						<p>예를 들어, 프로그래머는 이 프로그램에 넓이의 곱을 하는 연산을 추가하려고 한다.<br/>
						여러 가지 방법으로 AreaCalculator 클래스의 코드를 수정할 것이다.<br/>
						(예를 들어, 연산 과정에 if문을 추가할 수도 있을 것이다.)<br/>
						<br/>
						OCP원칙의 위배되어 새로운 기능을 추가하는데 있어서 수정을 해야함을 보여준다.</p>
						
						<p><br/>
						<br/>
						&nbsp;</p>
						
						<h1>#After branch</h1>
						
						<p><img alt="" src="/resources/image/item/contents/content_171205_132827_OCP after.jpg" style="height:240px; width:720px"/></p>
						
						<p>OCP원칙을 적용한 설계는 다음과 같이 연관 관계를 수정 할 수 있다.<br/>
						가장 중요한것은 무엇이 변하는 것인지를 확인 해야하는 것이다.<br/>
						<br/>
						<strong>클래스는 변화의 단위이다.</strong><br/>
						이때 변화는 &quot;연산&quot;이다.<br/>
						곱을 하는 연산과, 덧셈을 하는 연산 두 가지의 변화가 있다.<br/>
						하나의 연산이 곧 클래스의 단위인 것이다.<br/>
						<br/>
						OCP원칙에 위배되지 않음을 확인해보자.<br/>
						예를 들어, 넓이의 가장 큰 값을 구하는 연산을 추가해 보려고한다.<br/>
						AreaCalculator를 상속받는 AreaMaxCalculator 클래스를 생성해주면 될 것이다.<br/>
						새로운 기능을 추가하더라도, 기존의 코드는 전혀 수정되지 않음을 보여준다</p>
						
						<p>&nbsp;</p>
						
						<p>&nbsp;</p>
						
						<p><strong>AreaCalcualtor.java</strong>&nbsp;</p>
						
						<pre>
						<code>package com.cglee079.ocp;
						
						import java.util.ArrayList;
						
						public abstract class AreaCalculator {
							protected ArrayList shapes = new ArrayList();
							protected double areasum;
						
							public abstract void calculate();
						
							public double getAreasum() {
								return areasum;
							}
						
							public void addShape(Shape s) {
								shapes.add(s);
							}
						}
						</code></pre>
						
						<p><br/>
						<strong>AreaProdCalculator.java</strong></p>
						
						<pre>
						<code>package com.cglee079.ocp;
						
						import java.util.Iterator;
						
						public class AreaProdCalculator extends AreaCalculator {
						
							public void calculate() {
								Iterator iter = shapes.iterator();
								areasum = 1.0;
								while (iter.hasNext()) {
									Shape curShape = iter.next();
									areasum *= curShape.area();
								}
							}
						}
						</code></pre>
						
						<p>상위 클래스인 AreaCalculator 클래스를 상속받는다.<br/>
						추상메소드인 calculate()(연산)메소드를 하위 클래스에서 구현한다</p>
				</div>
			</div>
		</div>
			
		
		<c:import url="../included/included_footer.jsp" charEncoding="UTF-8">
		</c:import>
		
		<c:if test="${!empty beforeItem}">
			<div class="btn btn-item-before h-reverse" style="background-image: url(${pageContext.request.contextPath}/resources/image/btn_item_arrow.png)"></div>
		</c:if>
		
		<c:if test="${!empty afterItem}">
			<div class="btn btn-item-next" style="background-image: url(${pageContext.request.contextPath}/resources/image/btn_item_arrow.png)"></div>
		</c:if>
	</div>
</body>
</html>