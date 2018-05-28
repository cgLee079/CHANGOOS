/* Call drawPager(), if you want get Pager */

/*****  CASE ID  ******/
const	ONE_PG_GRP		= 0,
		START_PG_GRP	= 1,
		CENTER_PG_GRP	= 2,
		LAST_PG_GRP		= 3;
/***** ^^^^^^^^^ ****/

/************* Set Custom **************/
const	FIRST_MOVE_TAG		= '[처음]',	// first move
		LAST_MOVE_TAG		= '[마지막]',	// last move
		PREV_MOVE_TAG		= '[이전]',	// prev move
		NEXT_MOVE_TAG		= '[다음]',	// next move
		MOVE_TAG_FONT_SIZE	= '0.5rem',	// move tag text size
		PAGER_FONT_SIZE		= '1rem',	// pager font size
		PAGER_CUR_FONT_SIZE	= '1rem',	// current page font size
		PAGER_CUR_COLOR		= '#000',	// current page font color
		PAGER_CUR_FONT_WEIG	= '700',	// current page font bold
		LR_MARGIN			= '0.5rem',	// All tag left, right padding
		TAG_OUT_COLOR		= '#888', 	// All tag hover out
		TAG_IN_COLOR		= '#000';	// All tag hover in 
/***** ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ ****/

/** 
 * @param {String}	callFunc 	callFunc is function's name, it is called when pager is clicked
 * 							 	callFunc has one param (page), callFunc(page).		EX. pageMove(1)
 * @param {int} 	curPg 		curret Page
 * @param {int} 	allRowCnt	How many row?
 * @param {int} 	perPgLine	In each Page, How many row?  
 * @param {int} 	pgGrpCnt	In each Pager Group, How many pager?
 * 									EX. when pgGrpCnt == 3,	{1 2 3} {4 5 6} {7 8 9}
 */
function drawPager(callFunc, curPg, allRowCnt, perPgLin, pgGrpCnt){
	var	div			= $('<div>');	// container
	var	totalPager	= undefined;	// total Page 
	var	cPageGrp	= undefined;	// current Page Grp
	var	tPageGrp	= undefined; 	// total Page Grp 
	var	sPageGrp	= 0;			// strat Page Grp
	var	lPageGrp	= undefined;	// last Page Grp
	var	caseID		= undefined;	// 0,1,2,3
	var	hTag		= undefined;	// first move tag, prev move tag
	var	tTag		= undefined;	// next move tag, last move tag
	var	pager		= undefined;	// pager
	
	if(allRowCnt <= perPgLin){
		return div;
	}
	
	totalPager = parseInt(allRowCnt / perPgLin);
	if (allRowCnt % perPgLin > 0 || totalPager == 0) {
		totalPager++;
	}
	cPageGrp	= parseInt((curPg - 1) / pgGrpCnt); // 
	tPageGrp	= parseInt((totalPager - 1) / pgGrpCnt);	
	lPageGrp	= tPageGrp;
	
	if(tPageGrp == 0){caseID = ONE_PG_GRP;} //case0. only one page Grp
	else if(cPageGrp == sPageGrp){ caseID = START_PG_GRP; }//case1. next, last Tag
	else if(cPageGrp != sPageGrp && cPageGrp != lPageGrp){ caseID = CENTER_PG_GRP; } //case2. first, prev, next, last Tag
	else if(cPageGrp == lPageGrp){ caseID = LAST_PG_GRP;} //case3. first, prev Tag
	
	hTag	= getHeaderTag(caseID, callFunc, curPg, totalPager, cPageGrp, pgGrpCnt);
	tTag	= getTailTag(caseID, callFunc, curPg, totalPager, cPageGrp, pgGrpCnt);
	pager	= getPager(caseID, callFunc, curPg, totalPager, cPageGrp, pgGrpCnt);
	
	/* NO Change Order */
	for(var i in hTag)	{ div.append(hTag[i]); }
	for(var i in pager)	{ div.append(pager[i]); }
	for(var i in tTag)	{ div.append(tTag[i]); }
	/* ^^^^^^^^^^^^^^^ */
	
	return div;
}

function getBasicTag(fontSize){
	var tag	= $('<a>',{ // Default Style
		style	: 'font-size	: ' + fontSize + '; '
				+ 'margin		: 0px ' + LR_MARGIN + '; '
				+ 'color		: ' + TAG_OUT_COLOR + '; '
				+ 'cursor		: pointer; '
	});
	
	tag.hover(function(){ // Add Hover Event
		$(this).css('color', TAG_IN_COLOR);
	},function(){
		$(this).css('color', TAG_OUT_COLOR);
	});
	
	return tag;
}

function getMoveTag(fontSize){
	var tag	= $('<a>',{ // Default Style
		style	: 'font-size	: ' + fontSize + '; '
				+ 'margin		: 0px 0.15rem; '
				+ 'color		: ' + TAG_OUT_COLOR + '; '
				+ 'cursor		: pointer; '
	});
	
	tag.hover(function(){ // Add Hover Event
		$(this).css('color', TAG_IN_COLOR);
	},function(){
		$(this).css('color', TAG_OUT_COLOR);
	});
	
	return tag;
}

function getHeaderTag(caseID, callFunc, curPg, totalPager, cPageGrp, pgGrpCnt){
	var tag = new Array();
		
	var firstMoveTag	= getMoveTag(MOVE_TAG_FONT_SIZE).text(FIRST_MOVE_TAG);
	var prevMoveTag		= getMoveTag(MOVE_TAG_FONT_SIZE).text(PREV_MOVE_TAG);
	var sPager			= 1;
	var prevLPager		= (cPageGrp) * pgGrpCnt;

	if(caseID == CENTER_PG_GRP || caseID == LAST_PG_GRP){
		firstMoveTag.attr('onclick', callFunc + '(' + sPager + ')');
		prevMoveTag.attr('onclick', callFunc + '(' + prevLPager + ')');
	} else{
		firstMoveTag.css("opacity", '0');
		prevMoveTag.css("opacity", '0');
		
		firstMoveTag.css("cursor", 'default');
		prevMoveTag.css("cursor", 'default');
	}
	
	tag.push(firstMoveTag);
	tag.push(prevMoveTag);
	
	return tag;
}

function getTailTag(caseID, callFunc, curPg, totalPager, cPageGrp, pgGrpCnt){
	var tag = new Array();
	var nextMoveTag	= getMoveTag(MOVE_TAG_FONT_SIZE).text(NEXT_MOVE_TAG);
	var lastMoveTag	= getMoveTag(MOVE_TAG_FONT_SIZE).text(LAST_MOVE_TAG);
	var nxtSPager 	= ((cPageGrp+1)* pgGrpCnt) + 1;
	var lPager		= totalPager;
	
	if(caseID == START_PG_GRP || caseID == CENTER_PG_GRP){
		nextMoveTag.attr('onclick', callFunc + '(' + lPager + ')');
		lastMoveTag.attr('onclick', callFunc + '(' + nxtSPager + ')');
	} else{
		nextMoveTag.css("opacity", '0');
		lastMoveTag.css("opacity", '0');
		
		nextMoveTag.css("cursor", 'default');
		lastMoveTag.css("cursor", 'default');
	}
	
	tag.push(nextMoveTag);
	tag.push(lastMoveTag);

	return tag;
}

function getPager(caseID, callFunc, curPg, totalPager, cPageGrp, pgGrpCnt){
	var sPager	= (cPageGrp * pgGrpCnt) + 1,
		lPager	= ((cPageGrp+1) * pgGrpCnt),
		pager	= new Array(),
		tag		= undefined;
	
	if(caseID == ONE_PG_GRP || caseID == LAST_PG_GRP){
		lPager = totalPager;
	}

	for (var i = sPager; i <= lPager; i++){
		tag	= getBasicTag(PAGER_FONT_SIZE).text(i);
		
		
		if(tag.text() == curPg){ // Current Page
			tag.unbind('mouseenter mouseleave');	//Remove Hover Event
			tag.css('color', PAGER_CUR_COLOR);
			tag.css('font-size', PAGER_CUR_FONT_SIZE);
			tag.css('font-weight', PAGER_CUR_FONT_WEIG);
			tag.css('cursor', 'default');
		}else{
			tag.attr('onClick', callFunc + '(' + i + ')');
		}
		
		pager.push(tag);
	}
	
	return pager;
}
