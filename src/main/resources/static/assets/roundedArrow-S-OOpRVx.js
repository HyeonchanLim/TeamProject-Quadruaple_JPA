import{q as y}from"./index-CclgMCKg.js";function m(c){const{sizePopupArrow:h,borderRadiusXS:r,borderRadiusOuter:t}=c,o=h/2,a=0,n=o,s=t*1/Math.sqrt(2),e=o-t*(1-1/Math.sqrt(2)),d=o-r*(1/Math.sqrt(2)),u=t*(Math.sqrt(2)-1)+r*(1/Math.sqrt(2)),$=2*o-d,p=u,l=2*o-s,w=e,g=2*o-a,b=n,x=o*Math.sqrt(2)+t*(Math.sqrt(2)-2),i=t*(Math.sqrt(2)-1),f=`polygon(${i}px 100%, 50% ${i}px, ${2*o-i}px 100%, ${i}px 100%)`,q=`path('M ${a} ${n} A ${t} ${t} 0 0 0 ${s} ${e} L ${d} ${u} A ${r} ${r} 0 0 1 ${$} ${p} L ${l} ${w} A ${t} ${t} 0 0 0 ${g} ${b} Z')`;return{arrowShadowWidth:x,arrowPath:q,arrowPolygon:f}}const A=(c,h,r)=>{const{sizePopupArrow:t,arrowPolygon:o,arrowPath:a,arrowShadowWidth:n,borderRadiusXS:s,calc:e}=c;return{pointerEvents:"none",width:t,height:t,overflow:"hidden","&::before":{position:"absolute",bottom:0,insetInlineStart:0,width:t,height:e(t).div(2).equal(),background:h,clipPath:{_multi_value_:!0,value:[o,a]},content:'""'},"&::after":{content:'""',position:"absolute",width:n,height:n,bottom:0,insetInline:0,margin:"auto",borderRadius:{_skip_check_:!0,value:`0 0 ${y(s)} 0`},transform:"translateY(50%) rotate(-135deg)",boxShadow:r,zIndex:0,background:"transparent"}}};export{A as a,m as g};
