# Ticket View

The application contains a custom view for ticket.


#### Screenshot:
<img src="https://github.com/bxute/TicketView/blob/master/qr.png" width="400px" height="800px">


### How cuts are made ?
ClipRect enables us to select the region and then perform `DIFFERENCE` , `UNION` , `INTERSECT` etc. with the canvas.

In order to get the cuts, we need to provide 2 circular regions at both end and then apply `DIFFERENCE`.

### Reference
 - https://developer.android.com/reference/android/graphics/Canvas