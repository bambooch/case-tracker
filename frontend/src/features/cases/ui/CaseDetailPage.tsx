import { useParams } from 'react-router-dom'

import { useParties } from '../../parties/application/useParties'
import { CaseDetailPanel } from './components/CaseDetailPanel'

export function CaseDetailPage() {
  const { id } = useParams<{ id: string }>()
  const { parties } = useParties()
  const caseId = id ? parseInt(id, 10) : NaN

  if (isNaN(caseId)) {
    return (
      <div className="px-4 py-8 sm:px-6 lg:px-8">
        <div className="mx-auto max-w-4xl">
          <p className="text-sm font-semibold text-rose-700" role="alert">
            Nevažeći ID predmeta.
          </p>
        </div>
      </div>
    )
  }

  return (
    <div className="px-4 py-8 sm:px-6 lg:px-8">
      <div className="mx-auto max-w-4xl space-y-6">
        <CaseDetailPanel caseId={caseId} parties={parties} />
      </div>
    </div>
  )
}
